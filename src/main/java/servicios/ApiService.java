package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.ProyectoDto;
import dtos.UsuarioDto;

/**
 * Clase ApiService que se encarga de gestionar las solicitudes HTTP hacia la API REST.
 * Realiza acciones como obtener, actualizar y eliminar proyectos, así como registrar usuarios y gestionar la autenticación.
 */
public class ApiService {

    private static final String API_BASE_URL = "http://localhost:8081/apiBoostly/api";
    private final ObjectMapper mapper;

    // Constructor que inicializa el ObjectMapper con los módulos necesarios para el manejo de fechas
    public ApiService() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Método general para hacer solicitudes HTTP a la API REST.
     * @param url La URL a la que se realizará la solicitud.
     * @param method El método HTTP a utilizar (GET, POST, PUT, DELETE).
     * @param jsonInput El cuerpo de la solicitud, en formato JSON, o null si no se necesita.
     * @return La respuesta de la API en formato JSON, o null si la solicitud no fue exitosa.
     * @throws Exception En caso de que ocurra algún error al hacer la solicitud.
     */
    private String sendHttpRequest(String url, String method, String jsonInput) throws Exception {
        URI uri = new URI(url);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        if (jsonInput != null) {
            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return readResponse(connection);
        }
        return null;
    }

    /**
     * Lee la respuesta de la API y la convierte a un String.
     * @param connection La conexión HTTP.
     * @return La respuesta de la API.
     * @throws Exception En caso de que ocurra un error al leer la respuesta.
     */
    private String readResponse(HttpURLConnection connection) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    /**
     * Elimina un proyecto de la base de datos.
     * @param id El ID del proyecto a eliminar.
     * @return true si el proyecto fue eliminado correctamente, false si ocurrió un error.
     */
    public boolean eliminarProyecto(Long id) {
        try {
            return sendHttpRequest(API_BASE_URL + "/proyectos/" + id, "DELETE", null) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene un proyecto por su ID.
     * @param idProyecto El ID del proyecto a obtener.
     * @return El proyecto correspondiente al ID, o null si no se encuentra.
     */
    public ProyectoDto obtenerProyectoPorId(Long idProyecto) {
        try {
            String response = sendHttpRequest(API_BASE_URL + "/proyectos/" + idProyecto, "GET", null);
            return response != null ? mapper.readValue(response, ProyectoDto.class) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Actualiza los datos de un proyecto en la base de datos.
     * @param proyectoDto El DTO que contiene los nuevos datos del proyecto.
     * @return true si el proyecto fue actualizado correctamente, false si ocurrió un error.
     */
    public boolean actualizarProyecto(ProyectoDto proyectoDto) {
        try {
            String jsonInput = mapper.writeValueAsString(proyectoDto);
            return sendHttpRequest(API_BASE_URL + "/proyectos/" + proyectoDto.getIdProyecto(), "PUT",
                    jsonInput) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una lista de proyectos asociados a un usuario.
     * @param idUsuario El ID del usuario para el que se deben obtener los proyectos.
     * @return Una lista de proyectos del usuario, o null si no se encuentran proyectos.
     */
    public List<ProyectoDto> obtenerProyectosPorUsuario(Long idUsuario) {
        try {
            String response = sendHttpRequest(API_BASE_URL + "/proyectos/usuario/" + idUsuario, "GET", null);
            return response != null ? Arrays.asList(mapper.readValue(response, ProyectoDto[].class)) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene un usuario por su ID.
     * @param idUsuario El ID del usuario a obtener.
     * @return El usuario correspondiente al ID, o null si no se encuentra.
     */
    public UsuarioDto obtenerUsuarioPorId(Long idUsuario) {
        try {
            String response = sendHttpRequest(API_BASE_URL + "/usuarios/" + idUsuario, "GET", null);
            return response != null ? mapper.readValue(response, UsuarioDto.class) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Registra un proyecto en la base de datos.
     * @param proyectoDto El DTO que contiene los datos del proyecto a registrar.
     * @return La respuesta de la API en formato String.
     */
    public String registroProyecto(ProyectoDto proyectoDto) {
        try {
            String jsonInput = mapper.writeValueAsString(proyectoDto);
            return sendHttpRequest(API_BASE_URL + "/proyectos", "POST", jsonInput);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * Realiza el login de un usuario utilizando sus credenciales de Google.
     * @param usuDto El DTO que contiene los datos del usuario que intenta iniciar sesión.
     * @return La respuesta de la API en formato String.
     */
    public String loginGoogle(UsuarioDto usuDto) {
        try {
            String jsonInput = mapper.writeValueAsString(usuDto);
            return sendHttpRequest(API_BASE_URL + "/usuarios/loginGoogle", "POST", jsonInput);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    /**
     * Obtiene un usuario a partir de su correo electrónico.
     * @param email El correo electrónico del usuario.
     * @return El usuario correspondiente al correo, o null si no se encuentra.
     */
    public UsuarioDto obtenerUsuarioPorEmail(String email) {
        try {
            String response = sendHttpRequest(API_BASE_URL + "/usuarios/email/" + email, "GET", null);
            return response != null ? mapper.readValue(response, UsuarioDto.class) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Registra un usuario en la base de datos.
     * @param usuDto El DTO que contiene los datos del usuario a registrar.
     * @return La respuesta de la API en formato String.
     */
    public String registroUsuario(UsuarioDto usuDto) {
        try {
            String jsonInput = mapper.writeValueAsString(usuDto);
            return sendHttpRequest(API_BASE_URL + "/usuarios", "POST", jsonInput);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
