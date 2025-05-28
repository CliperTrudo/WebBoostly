package paypal;

import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Collections;

@WebServlet("/create-order")
public class CreateOrderServlet extends PayPalClient {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String amount = req.getParameter("amount");
        // Construye el contexto con tus URLs reales
        String baseUrl = req.getScheme() + "://" +
                         req.getServerName() + ":" +
                         req.getServerPort() +
                         req.getContextPath();

        ApplicationContext appContext = new ApplicationContext()
            .brandName("Mi Tienda Test")
            .landingPage("BILLING")            // o LOGIN
            .cancelUrl(baseUrl + "/pay.jsp")  // si el comprador cancela vuelve al formulario
            .returnUrl(baseUrl + "/capture-order") // tras aprobar va a este servlet
            .userAction("PAY_NOW")            // muestra botón “Pagar ahora”
        ;

        OrderRequest orderRequest = new OrderRequest()
            .checkoutPaymentIntent("CAPTURE")
            .applicationContext(appContext)
            .purchaseUnits(Collections.singletonList(
                new PurchaseUnitRequest()
                  .amountWithBreakdown(
                    new AmountWithBreakdown()
                      .currencyCode("EUR")
                      .value(amount)
                  )
            ));

        OrdersCreateRequest request = new OrdersCreateRequest()
            .requestBody(orderRequest);

        HttpResponse<Order> response = client.execute(request);

        String approveLink = response.result()
            .links().stream()
            .filter(l -> "approve".equals(l.rel()))
            .findFirst()
            .orElseThrow()
            .href();

        resp.sendRedirect(approveLink);
    }
}
