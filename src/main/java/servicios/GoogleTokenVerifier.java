package servicios;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Collections;

/**
 * Clase encargada de verificar la validez de un token de Google.
 * Utiliza la API de Google para comprobar si un token de identificación (ID Token) es válido.
 * El cliente ID debe ser el correspondiente a tu aplicación registrada en Google.
 */
public class GoogleTokenVerifier {

    // Client ID para la autenticación de Google. Debe ser el ID de tu aplicación.
    private static final String CLIENT_ID = "797777584256-rv70sv8lpr6pvl9bbki0b029p0fse5se.apps.googleusercontent.com"; // Cambia esto por tu Client ID
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();  // Factoria de JSON de Google para el parseo del token

    /**
     * Método encargado de verificar la validez de un ID Token recibido de Google.
     * 
     * @param idTokenString El ID token que se va a verificar.
     * @return El GoogleIdToken si el token es válido.
     * @throws Exception Si el token es inválido o hay un error durante la verificación.
     */
    public static GoogleIdToken verifyToken(String idTokenString) throws Exception {

        // Configuración del verificador de GoogleIdToken usando el Client ID y el JSON factory
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID))  // Establecemos el Client ID para la verificación
                .build();

        // Verificamos el token
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken; // El token es válido, devolvemos el GoogleIdToken
        } else {
            throw new IllegalArgumentException("Token inválido");  // Si el token no es válido, lanzamos una excepción
        }
    }
}
