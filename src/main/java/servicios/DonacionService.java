package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.DonacionDto;

/**
 * DonacionService
 *
 * Servicio que actúa como cliente HTTP desde WebBoostly hacia la API REST de ApiBoostly
 * para gestionar las operaciones CRUD de Donaciones. Usa HttpURLConnection para enviar y
 * recibir JSON, y Jackson para el mapeo DTO ↔ JSON.
 *
 * ENDPOINT BASE DE LA API (ajusta según tu configuración real):
 *   http://localhost:8081/apiBoostly/api
 */
public class DonacionService {

    private static final String API_BASE_URL = "http://localhost:8081/apiBoostly/api";
    private final ObjectMapper mapper;

    public DonacionService() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Hace una petición HTTP genérica a la API REST.
     *
     * @param urlString La URL completa a la que se realiza la petición.
     * @param method    El método HTTP (GET, POST, PUT, DELETE).
     * @param jsonInput Cuerpo de la petición en JSON, o null si no se envía body.
     * @return La respuesta de la API como String (JSON) o null si no es HTTP 200/201.
     * @throws Exception En caso de error en la conexión o lectura.
     */
    private String sendHttpRequest(String urlString, String method, String jsonInput) throws Exception {
        URI uri = new URI(urlString);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        if (jsonInput != null) {
            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonInput.getBytes("UTF-8"));
                os.flush();
            }
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK ||
            responseCode == HttpURLConnection.HTTP_CREATED) {
            return readResponse(connection);
        }
        return null;
    }

    /**
     * Lee la respuesta de la API y la devuelve como String.
     *
     * @param connection La conexión HTTP abierta.
     * @return El contenido de la respuesta como String.
     * @throws Exception En caso de error al leer la respuesta.
     */
    private String readResponse(HttpURLConnection connection) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    /**
     * Crea una nueva donación en el backend (ApiBoostly).
     * Realiza un POST a /donaciones con el DonacionDto en el body.
     *
     * @param donacionDto DTO con los datos de la donación a guardar.
     * @return DonacionDto resultante (incluyendo idDonacion y fechaCreacion) o null si falla.
     */
    public DonacionDto crearDonacion(DonacionDto donacionDto) {
        try {
            String jsonInput = mapper.writeValueAsString(donacionDto);
            String response = sendHttpRequest(API_BASE_URL + "/donaciones", "POST", jsonInput);
            return response != null ? mapper.readValue(response, DonacionDto.class) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene las donaciones pendientes de un proyecto (estado = "PENDIENTE_AUTORIZACION").
     * Realiza un GET a /donaciones/proyecto/{proyectoId}?estado=PENDIENTE_AUTORIZACION
     *
     * @param proyectoId ID del proyecto.
     * @return Lista de DonacionDto (array JSON) o empty list si no hay o falla.
     */
    public List<DonacionDto> obtenerDonacionesPendientes(Long proyectoId) {
        try {
            String requestUrl = API_BASE_URL + "/donaciones/proyecto/" + proyectoId
                    + "?estado=PENDIENTE_AUTORIZACION";
            String response = sendHttpRequest(requestUrl, "GET", null);
            if (response == null) {
                return new ArrayList<>();
            }
            DonacionDto[] arr = mapper.readValue(response, DonacionDto[].class);
            return Arrays.asList(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Actualiza una donación existente, por ejemplo para cambiar su estado a "CAPTURADA" o "ANULADA".
     * Realiza un PUT a /donaciones/{idDonacion} con el DonacionDto (al menos idDonacion y estado).
     *
     * @param donacionDto DTO con idDonacion y nuevos campos (p.ej. estado).
     * @return DonacionDto actualizado o null si falla.
     */
    public DonacionDto actualizarDonacion(DonacionDto donacionDto) {
        try {
            String jsonInput = mapper.writeValueAsString(donacionDto);
            String requestUrl = API_BASE_URL + "/donaciones/" + donacionDto.getIdDonacion();
            String response = sendHttpRequest(requestUrl, "PUT", jsonInput);
            return response != null ? mapper.readValue(response, DonacionDto.class) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
