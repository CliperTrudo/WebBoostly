package servicios;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Collections;

public class GoogleTokenVerifier {
    private static final String CLIENT_ID = "797777584256-rv70sv8lpr6pvl9bbki0b029p0fse5se.apps.googleusercontent.com"; // Cambia esto por tu Client ID
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static GoogleIdToken verifyToken(String idTokenString) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken; // Token válido
        } else {
            throw new IllegalArgumentException("Token inválido");
        }
    }
}
