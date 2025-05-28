package paypal;

import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/capture-order")
public class CaptureOrderServlet extends PayPalClient {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("token");

        // 1) Declaras la petición de captura
        OrdersCaptureRequest captureRequest = (OrdersCaptureRequest) new OrdersCaptureRequest(orderId)
            .requestBody(new OrderRequest());

        // 2) Ejecutas la petición y recibes HttpResponse<Order>
        HttpResponse<Order> captureResponse = client.execute(captureRequest);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<h1>¡Pago exitoso!</h1>");
        resp.getWriter().println("<pre>" + captureResponse.result() + "</pre>");
    }
}
