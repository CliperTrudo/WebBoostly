package paypal;

import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnit;
import com.paypal.orders.PurchaseUnitRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;

/**
 * CreateOrderServlet
 *
 * Recibe projectId, userId y amount desde el formulario,
 * arma un PurchaseUnitRequest, invoca a PayPal para crear la orden
 * (con la cabecera Prefer: return=representation) y redirige al link de aprobación.
 */
@WebServlet(name = "create-order", urlPatterns = "/create-order")
public class CreateOrderServlet extends PayPalClient {
    private static final long serialVersionUID = 1L;

    /**
     * Procesa la petición POST del formulario de donación.
     * Parámetros esperados:
     *   - projectId  (ID del proyecto que se donará)
     *   - userId     (ID del usuario que está donando)
     *   - amount     (monto de la donación, en formato disco “10.00”)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1) Leer los parámetros del formulario
        String projectIdParam = req.getParameter("projectId");
        String userIdParam    = req.getParameter("userId");
        String amountParam    = req.getParameter("amount");

        // Validación básica de presencia
        if (projectIdParam == null || userIdParam == null || amountParam == null
                || projectIdParam.isEmpty() || userIdParam.isEmpty() || amountParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Faltan parámetros obligatorios (projectId, userId o amount).");
            return;
        }

        long projectId;
        long userId;
        BigDecimal amount;
        try {
            projectId = Long.parseLong(projectIdParam);
            userId    = Long.parseLong(userIdParam);
            amount    = new BigDecimal(amountParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Formato inválido en projectId, userId o amount.");
            return;
        }

        // 2) Preparar el PurchaseUnitRequest con monto, moneda y custom_id
        PurchaseUnitRequest puReq = new PurchaseUnitRequest()
                // Opcional: referencia interna si la necesitas
                .referenceId("PU-" + projectId)
                // Concatenamos projectId:userId en custom_id para recuperarlo después
                .customId(projectId + ":" + userId)
                .amountWithBreakdown(
                        new AmountWithBreakdown()
                                .currencyCode("USD")
                                .value(amount.toPlainString())
                );

        // 3) Armar el OrderRequest (modo AUTHORIZE o CAPTURE según tu flujo)
        OrderRequest orderRequest = new OrderRequest()
                .checkoutPaymentIntent("AUTHORIZE")
                .purchaseUnits(Collections.singletonList(puReq));

        // 4) Configurar applicationContext (URLs de retorno/cancelación, marca, etc.)
        String requestURL = req.getRequestURL().toString();   // ej: http://localhost:8080/webboostly/create-order
        String servletPath = req.getServletPath();            // => "/create-order"
        String baseUrl = requestURL.replace(servletPath, ""); // => "http://localhost:8080/webboostly"

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("MiSitio")
                .landingPage("NO_PREFERENCE")
                .cancelUrl(baseUrl + "/cancel")
                .returnUrl(baseUrl + "/authorize-order")
                .userAction("CONTINUE");
        orderRequest.applicationContext(applicationContext);

        // 5) Crear la orden en PayPal **pidiendo la representación completa**:
        //    para que devuelva también “purchase_units” en el JSON de respuesta.
        OrdersCreateRequest request = (OrdersCreateRequest) new OrdersCreateRequest()
                .header("prefer", "return=representation")
                .requestBody(orderRequest);

        // (a) Ya no hay: PayPalClient client = new PayPalClient();
        //     Porque esta clase extiende de PayPalClient y hereda 'protected PayPalHttpClient client;'

        HttpResponse<Order> response;
        try {
            // (b) Usa el campo `client` heredado desde PayPalClient
            response = this.client.execute(request);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY,
                    "Error al crear la orden en PayPal: " + e.getMessage());
            return;
        }

        // 6) Depuración: imprime en consola toda la respuesta de PayPal
        System.out.println("---- PAYPAL CREATE ORDER RESPONSE ----");
        if (response == null) {
            System.out.println("  response == null");
        } else {
            Order orderResult = response.result();
            System.out.println("  HTTP Status Code: " + response.statusCode());
            if (orderResult == null) {
                System.out.println("  result() == null");
            } else {
                System.out.println("  Order ID: " + orderResult.id());
                System.out.println("  Status:   " + orderResult.status());

                // Ahora purchaseUnits() NO debe venir null ni vacío (por el header Prefer)
                if (orderResult.purchaseUnits() == null) {
                    System.out.println("  purchaseUnits() == null");
                } else if (orderResult.purchaseUnits().isEmpty()) {
                    System.out.println("  purchaseUnits() está vacío");
                } else {
                    System.out.println("  purchaseUnits().size() = " + orderResult.purchaseUnits().size());
                    // Recorremos cada PurchaseUnit (tipo com.paypal.orders.PurchaseUnit)
                    for (int i = 0; i < orderResult.purchaseUnits().size(); i++) {
                        PurchaseUnit pu = orderResult.purchaseUnits().get(i);
                        System.out.println("   - PurchaseUnit[" + i + "].referenceId = " + pu.referenceId());
                        System.out.println("   - PurchaseUnit[" + i + "].customId    = " + pu.customId());
                        if (pu.amountWithBreakdown() != null) {
                            System.out.println("   - PurchaseUnit[" + i + "].amount = " +
                                    pu.amountWithBreakdown().value());
                        }
                    }
                }
            }
        }
        System.out.println("----------------------------------------");

        // 7) Validar que realmente vino el array de purchaseUnits con al menos un elemento
        Order orderResult = response.result();
        if (orderResult == null
                || orderResult.purchaseUnits() == null
                || orderResult.purchaseUnits().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "PayPal devolvió purchaseUnits == null o vacío. Revisa consola para más detalles.");
            return;
        }

        // 8) Extraer el link de tipo “approve” para redirigir al usuario
        String approveLink = orderResult.links().stream()
                .filter(link -> "approve".equals(link.rel()))
                .findFirst()
                .map(LinkDescription::href)
                .orElse(null);

        if (approveLink == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se encontró el enlace de aprobación en la respuesta de PayPal.");
            return;
        }

        // 9) Redirigir al link de PayPal donde el usuario aprobará el pago
        resp.sendRedirect(approveLink);
    }
}
