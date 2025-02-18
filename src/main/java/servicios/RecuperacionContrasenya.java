// RecuperacionContrasenya.java - Servicio para manejar recuperación de contraseña en Boostly
package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RecuperacionContrasenya {

    private static final String API_URL = "http://localhost:8081/apiBoostly/api/usuarios";
    private static final EmailService emailService = new EmailService();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static String sendPostRequest(String url, String jsonInput) {
        try {
            URI uri = new URI(url);
            URL apiUrl = uri.toURL();

            HttpURLConnection conexion = (HttpURLConnection) apiUrl.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setDoOutput(true);

            try (OutputStream os = conexion.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                System.out.println("Error en la conexión: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static boolean enviarTokenRecuperacion(Object requestDto) {
        String jsonInput;
        try {
            jsonInput = mapper.writeValueAsString(requestDto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        String response = sendPostRequest(API_URL + "/generar-token", jsonInput);
        System.out.println(response);
        if (!response.equals("error")) {
            try {
                String email = mapper.readTree(jsonInput).get("email").asText();
                String token = mapper.readTree(jsonInput).get("tokenRecuperacion").asText();
                emailService.enviarCorreoRecuperacion(email, token);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean restablecerContrasena(Object requestDto) {
        String jsonInput;
        try {
            jsonInput = mapper.writeValueAsString(requestDto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return !sendPostRequest(API_URL + "/reset-password", jsonInput).equals("error");
    }
}
