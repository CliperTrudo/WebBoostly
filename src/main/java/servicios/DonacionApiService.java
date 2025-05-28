package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.DonacionDto;

/**
 * Servicio que se encarga de gestionar las solicitudes HTTP hacia la API REST
 * para crear y actualizar donaciones, usando HttpURLConnection y Jackson.
 */
public class DonacionApiService {

    private static final String API_BASE_URL =
        System.getenv().getOrDefault("API_BASE_URL", "http://localhost:8081/apiBoostly/api");
    private final ObjectMapper mapper;

    public DonacionApiService() {
        this.mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Método genérico para enviar peticiones HTTP.
     *
     * @param url       URL completa a la que llamar.
     * @param method    Verbo HTTP (GET, POST, PUT, DELETE).
     * @param jsonInput Cuerpo JSON a enviar, o null si no aplica.
     * @return Cadena JSON de respuesta, o null si el código HTTP no es 2xx.
     * @throws Exception en caso de error de conexión o de lectura.
     */
    private String sendHttpRequest(String url, String method, String jsonInput) throws Exception {
        URI uri = new URI(url);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        if (Objects.nonNull(jsonInput)) {
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }
        }

        int code = conn.getResponseCode();
        if (code >= 200 && code < 300) {
            try (BufferedReader in =
                     new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder resp = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    resp.append(line);
                }
                return resp.toString();
            }
        } else {
            return null;
        }
    }

    /**
     * Crea una nueva donación llamando a POST /donaciones.
     *
     * @param dto DonacionDto con orderId, importe y moneda.
     * @return DonacionDto con todos los campos devueltos por el servidor (incluido id, creadoEn, etc.),
     *         o null si la operación falla.
     */
    public DonacionDto crearDonacion(DonacionDto dto) {
        try {
            String jsonIn = mapper.writeValueAsString(dto);
            String resp = sendHttpRequest(API_BASE_URL + "/donaciones", "POST", jsonIn);
            return resp != null
                ? mapper.readValue(resp, DonacionDto.class)
                : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Actualiza una donación existente llamando a PUT /donaciones.
     *
     * @param dto DonacionDto con orderId, estado, payerId, payerEmail, capturadoEn.
     * @return DonacionDto actualizado según la respuesta del servidor,
     *         o null si la operación falla.
     */
    public DonacionDto actualizarDonacion(DonacionDto dto) {
        try {
            String jsonIn = mapper.writeValueAsString(dto);
            String resp = sendHttpRequest(API_BASE_URL + "/donaciones", "PUT", jsonIn);
            return resp != null
                ? mapper.readValue(resp, DonacionDto.class)
                : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
