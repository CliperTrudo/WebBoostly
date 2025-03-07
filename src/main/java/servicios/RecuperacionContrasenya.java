// RecuperacionContrasenya.java - Servicio para manejar recuperación de contraseña en Boostly
package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servicio que gestiona la recuperación de contraseñas para los usuarios de Boostly.
 * Este servicio permite generar un token de recuperación y restablecer la contraseña.
 */
public class RecuperacionContrasenya {

    // URL base de la API para manejar las solicitudes de recuperación de contraseñas
    private static final String API_URL = "http://localhost:8081/apiBoostly/api/usuarios";
    
    // Servicio para el envío de correos electrónicos
    private static final EmailService emailService = new EmailService();
    
    // Instancia de ObjectMapper para convertir objetos Java a JSON y viceversa
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Método privado para realizar solicitudes POST a la API.
     * 
     * @param url La URL a la que se realizará la solicitud POST.
     * @param jsonInput El cuerpo de la solicitud en formato JSON.
     * @return La respuesta de la API o "error" si algo falla.
     */
    private static String sendPostRequest(String url, String jsonInput) {
        try {
            // Crear la URI desde la URL
            URI uri = new URI(url);
            URL apiUrl = uri.toURL();

            // Abrir la conexión HTTP
            HttpURLConnection conexion = (HttpURLConnection) apiUrl.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setDoOutput(true);

            // Escribir el cuerpo de la solicitud
            try (OutputStream os = conexion.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            // Leer la respuesta de la API
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) { // Si la solicitud fue exitosa
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) { // Leer cada línea de la respuesta
                    response.append(inputLine);
                }
                in.close(); // Cerrar el BufferedReader
                return response.toString();
            } else {
                System.out.println("Error en la conexión: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Imprimir el error si ocurre una excepción
        }
        return "error";  // Retornar error si algo falla
    }

    /**
     * Método para generar un token de recuperación y enviar un correo de recuperación.
     * 
     * @param requestDto El DTO que contiene la información necesaria para generar el token.
     * @return true si el proceso fue exitoso, false si hubo un error.
     */
    public static boolean enviarTokenRecuperacion(Object requestDto) {
        String jsonInput;
        try {
            // Convertir el DTO a formato JSON
            jsonInput = mapper.writeValueAsString(requestDto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        // Hacer la solicitud POST para generar el token
        String response = sendPostRequest(API_URL + "/generar-token", jsonInput);
        System.out.println(response);
        
        if (!response.equals("error")) {
            try {
                // Obtener el email y el token del cuerpo de la solicitud JSON
                String email = mapper.readTree(jsonInput).get("email").asText();
                String token = mapper.readTree(jsonInput).get("tokenRecuperacion").asText();
                
                // Enviar el correo con el token de recuperación
                emailService.enviarCorreoRecuperacion(email, token);
                return true;  // Si el correo fue enviado correctamente
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;  // Si algo falla en el proceso
    }

    /**
     * Método para restablecer la contraseña de un usuario.
     * 
     * @param requestDto El DTO con la nueva contraseña y el token de recuperación.
     * @return true si el proceso fue exitoso, false si hubo un error.
     */
    public static boolean restablecerContrasena(Object requestDto) {
        String jsonInput;
        try {
            // Convertir el DTO a formato JSON
            jsonInput = mapper.writeValueAsString(requestDto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        // Hacer la solicitud POST para restablecer la contraseña
        return !sendPostRequest(API_URL + "/reset-password", jsonInput).equals("error");
    }
}
