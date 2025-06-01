package paypal;

import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import dtos.DonacionDto;
import servicios.DonacionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * AuthorizeOrderServlet
 *
 * Flujo:
 *   1) Recibe el parámetro "token" en la URL (/authorize-order?token={ORDER_ID})
 *   2) Llama a PayPal para autorizar la orden (POST /v2/checkout/orders/{orderId}/authorize)
 *   3) Luego llama a PayPal para obtener la representación completa de la orden (GET /v2/checkout/orders/{orderId})
 *      usando la cabecera "Prefer: return=representation", para así poder leer purchaseUnits[].custom_id y amount.
 *   4) Valida que el customId esté presente y bien formado ("projectId:userId")
 *   5) Extrae projectId, userId, amount y authorizationId
 *   6) Crea el DonacionDto y lo envía al DonacionService para persistirlo
 *   7) Finalmente responde con una página HTML de confirmación.
 */
@WebServlet("/authorize-order")
public class AuthorizeOrderServlet extends PayPalClient {
    private static final long serialVersionUID = 1L;

    // Servicio de donaciones: encapsula la llamada HTTP a tu API REST (ApiBoostly)
    private DonacionService donacionService = new DonacionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) Recuperar "token" (orderId) de la URL: /authorize-order?token={ORDER_ID}
        String orderId = req.getParameter("token");
        if (orderId == null || orderId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID (token) faltante.");
            return;
        }

        // 2) Crear el objeto OrdersAuthorizeRequest para autorizar la orden
        OrdersAuthorizeRequest authorizeRequest = (OrdersAuthorizeRequest) new OrdersAuthorizeRequest(orderId)
                // Nota: la llamada a authorize NO regresa customId en purchaseUnits por defecto,
                // por eso haremos luego un GET separado para obtener la información completa.
                .requestBody(new OrderRequest());

        // 3) Ejecutar la autorización en PayPal
        HttpResponse<Order> authorizeResponse;
        try {
            authorizeResponse = this.client.execute(authorizeRequest);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY,
                    "Error al comunicarse con PayPal para autorizar la orden: " + e.getMessage());
            return;
        }

        // 4) Verificar que la autorización devolvió al menos un PurchaseUnit con Authorization
        Order partialOrder = authorizeResponse.result();
        if (partialOrder == null
                || partialOrder.purchaseUnits() == null
                || partialOrder.purchaseUnits().isEmpty()
                || partialOrder.purchaseUnits().get(0).payments() == null
                || partialOrder.purchaseUnits().get(0).payments().authorizations() == null
                || partialOrder.purchaseUnits().get(0).payments().authorizations().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Respuesta inesperada de PayPal: no se encontró authorizationId.");
            return;
        }

        // 5) Extraer el authorizationId de la respuesta de la autorización
        String authorizationId = partialOrder
                .purchaseUnits()
                .get(0)
                .payments()
                .authorizations()
                .get(0)
                .id();

        // ------------------------------------------------------
        // 6) Ahora recuperamos la representación completa de la orden con un GET
        //    para poder leer "customId" y "amount" correctamente.
        //
        //    SIN ESTA LLAMADA, partialOrder.purchaseUnits().get(0).customId() = null.
        // ------------------------------------------------------
        OrdersGetRequest getRequest = (OrdersGetRequest) new OrdersGetRequest(orderId)
                .header("prefer", "return=representation");
        HttpResponse<Order> getResponse;
        try {
            getResponse = this.client.execute(getRequest);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY,
                    "Error al obtener la representación completa de la orden en PayPal: " + e.getMessage());
            return;
        }

        Order fullOrder = getResponse.result();
        if (fullOrder == null
                || fullOrder.purchaseUnits() == null
                || fullOrder.purchaseUnits().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se pudo obtener purchaseUnits de la orden después de autorizar.");
            return;
        }

        PurchaseUnit pu = fullOrder.purchaseUnits().get(0);

        // 7) Extraer el customId (esperamos "projectId:userId")
        String customId = pu.customId();
        if (customId == null || !customId.contains(":")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "customId ausente o mal formado en la orden PayPal. customId='" + customId + "'");
            return;
        }

        // 8) Partir customId en projectId y userId
        String[] parts = customId.split(":");
        if (parts.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "customId mal formado. Se esperaba 'projectId:userId', se obtuvo: '" + customId + "'");
            return;
        }

        Long projectId;
        Long userId;
        try {
            projectId = Long.parseLong(parts[0]);
            userId    = Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "customId contiene IDs no numéricos: '" + customId + "'");
            return;
        }

        // 9) Extraer el importe ("value") del PurchaseUnit
        AmountWithBreakdown amountWithBreakdown = pu.amountWithBreakdown();
        if (amountWithBreakdown == null || amountWithBreakdown.value() == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se encontró el monto en la orden de PayPal.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountWithBreakdown.value());
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "El importe recibido de PayPal no es un número válido: '" + amountWithBreakdown.value() + "'");
            return;
        }

        // 10) Construir el DonacionDto y persistir la donación
        DonacionDto donacionDto = new DonacionDto();
        donacionDto.setProyectoId(projectId);
        donacionDto.setUsuarioId(userId);
        donacionDto.setAuthorizationId(authorizationId);
        donacionDto.setOrderId(orderId);
        donacionDto.setAmount(amount);
        donacionDto.setEstado("PENDIENTE_AUTORIZACION");

        DonacionDto guardada = donacionService.crearDonacion(donacionDto);
        if (guardada == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al persistir la donación en la base de datos.");
            return;
        }

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // 11) Responder con HTML de confirmación
     // Agregamos un poco de CSS inline para mejorar la apariencia
        out.println("    <style>");
        out.println("      body {");
        out.println("        background-color: #f7f7f7;");
        out.println("        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;");
        out.println("        margin: 0;");
        out.println("        padding: 0;");
        out.println("        display: flex;");
        out.println("        align-items: center;");
        out.println("        justify-content: center;");
        out.println("        height: 100vh;");
        out.println("      }");
        out.println("      .container {");
        out.println("        background-color: #ffffff;");
        out.println("        border-radius: 8px;");
        out.println("        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);");
        out.println("        max-width: 500px;");
        out.println("        width: 100%;");
        out.println("        padding: 30px;");
        out.println("        text-align: center;");
        out.println("      }");
        out.println("      h1 {");
        out.println("        color: #2c3e50;");
        out.println("        font-size: 1.8rem;");
        out.println("        margin-bottom: 20px;");
        out.println("      }");
        out.println("      p {");
        out.println("        color: #555555;");
        out.println("        line-height: 1.6;");
        out.println("        margin-bottom: 15px;");
        out.println("      }");
        out.println("      .highlight {");
        out.println("        color: #e74c3c;");
        out.println("        font-weight: bold;");
        out.println("      }");
        out.println("      .btn {");
        out.println("        display: inline-block;");
        out.println("        background-color: #3498db;");
        out.println("        color: #ffffff;");
        out.println("        text-decoration: none;");
        out.println("        padding: 10px 20px;");
        out.println("        border-radius: 4px;");
        out.println("        transition: background-color 0.2s ease-in-out;");
        out.println("        margin-top: 20px;");
        out.println("      }");
        out.println("      .btn:hover {");
        out.println("        background-color: #2980b9;");
        out.println("      }");
        out.println("    </style>");

        out.println("  </head>");
        out.println("  <body>");
        out.println("    <div class=\"container\">");
        // Encabezado
        out.println("      <h1>✅ ¡Donación autorizada exitosamente!</h1>");

        // Texto informativo
        out.println("      <p>Tu donación de <span class=\"highlight\">€ " 
            + String.format("%.2f", amount) 
            + "</span> ha sido autorizada correctamente.</p>");
        out.println("      <p>No se cobrará el importe hasta que el proyecto alcance su meta.</p>");

        // Datos de referencia
        out.println("      <p><strong>Authorization ID:</strong> " + authorizationId + "</p>");
        

        // Botón para regresar a la lista de proyectos
        out.println("      <a class=\"btn\" href=\"" 
            + req.getContextPath() 
            + "/proyectosCategoria\">← Volver a Proyectos</a>");

        out.println("    </div>  <!-- /.container -->");
        out.println("  </body>");
        out.println("</html>");
    }
}
