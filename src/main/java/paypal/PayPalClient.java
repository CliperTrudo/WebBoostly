package paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import java.io.IOException;

public abstract class PayPalClient extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected PayPalHttpClient client;

    @Override
    public void init() throws ServletException {
        super.init();
        // Carga variables de entorno (ej. con dotenv si lo usas)
        String clientId     = "Afudmer8vEP_j5eO8X4oV_XbfMno-L5Ncy7-ATMzE5Vt2OFFx377cxzQqnhomcln05Ux4rVcSMu3PvfA";
        String clientSecret = "EFS_v--In5SdRH1oFnAHEDIU2TDC2gmie78EJOwYktrSWOFhaXb0f1XAwdTRLPbpJeC72d6T-EOXW2Cp";
        PayPalEnvironment environment =
            new PayPalEnvironment.Sandbox(clientId, clientSecret);
        this.client = new PayPalHttpClient(environment);
    }
}
