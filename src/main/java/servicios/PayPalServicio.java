package servicios;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class PayPalServicio {

    private final PayPalHttpClient client;

    public PayPalServicio(String clientId, String clientSecret, String mode) {
        PayPalEnvironment env = "live".equalsIgnoreCase(mode)
            ? new PayPalEnvironment.Live(clientId, clientSecret)
            : new PayPalEnvironment.Sandbox(clientId, clientSecret);
        this.client = new PayPalHttpClient(env);
    }

    /**
     * Crea una orden de pago en PayPal y devuelve el orderId.
     */
    public String crearOrden(BigDecimal importe, String moneda) throws IOException {
        OrderRequest body = new OrderRequest()
            .checkoutPaymentIntent("CAPTURE")
            .purchaseUnits(List.of(
                new PurchaseUnitRequest()
                    .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(moneda)
                        .value(importe.toString()))
            ));
        OrdersCreateRequest req = new OrdersCreateRequest().requestBody(body);
        Order order = client.execute(req).result();
        return order.id();
    }

    /**
     * Captura una orden existente y devuelve la información completa de capturación.
     */
    public Order capturarOrden(String orderId) throws IOException {
        OrdersCaptureRequest req = new OrdersCaptureRequest(orderId);
        return client.execute(req).result();
    }
}
