package paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

import jakarta.servlet.http.HttpServlet;

/**
 * PayPalClient
 *
 * Clase base que configura el cliente HTTP de PayPal.
 * Cáda servlet que interactúe con la API de PayPal debe extender de esta clase.
 */
public abstract class PayPalClient extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Cliente HTTP de PayPal
    protected PayPalHttpClient client ;

    public PayPalClient() {
        // 1) Configura tu entorno de PayPal (Sandbox o Live)
        //    Reemplaza "TU_CLIENT_ID" y "TU_CLIENT_SECRET" por tus credenciales.
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
            "Afudmer8vEP_j5eO8X4oV_XbfMno-L5Ncy7-ATMzE5Vt2OFFx377cxzQqnhomcln05Ux4rVcSMu3PvfA",
            "EFS_v--In5SdRH1oFnAHEDIU2TDC2gmie78EJOwYktrSWOFhaXb0f1XAwdTRLPbpJeC72d6T-EOXW2Cp"
        );
        client = new PayPalHttpClient(environment);
    }
}
