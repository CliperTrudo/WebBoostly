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

public class ApiService {

	private static final String API_BASE_URL = "http://localhost:8081/apiBoostly/api";
	private final ObjectMapper mapper;

	public ApiService() {
		this.mapper = new ObjectMapper().registerModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	// Método general para hacer solicitudes HTTP
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

	public boolean eliminarProyecto(Long id) {
		try {
			return sendHttpRequest(API_BASE_URL + "/proyectos/" + id, "DELETE", null) != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ProyectoDto obtenerProyectoPorId(Long idProyecto) {
		try {
			String response = sendHttpRequest(API_BASE_URL + "/proyectos/" + idProyecto, "GET", null);
			return response != null ? mapper.readValue(response, ProyectoDto.class) : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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

	public List<ProyectoDto> obtenerProyectosPorUsuario(Long idUsuario) {
		try {
			String response = sendHttpRequest(API_BASE_URL + "/proyectos/usuario/" + idUsuario, "GET", null);
			return response != null ? Arrays.asList(mapper.readValue(response, ProyectoDto[].class)) : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public UsuarioDto obtenerUsuarioPorId(Long idUsuario) {
		try {
			String response = sendHttpRequest(API_BASE_URL + "/usuarios/" + idUsuario, "GET", null);
			return response != null ? mapper.readValue(response, UsuarioDto.class) : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String registroProyecto(ProyectoDto proyectoDto) {
		try {
			String jsonInput = mapper.writeValueAsString(proyectoDto);
			return sendHttpRequest(API_BASE_URL + "/proyectos", "POST", jsonInput);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String loginGoogle(UsuarioDto usuDto) {
		try {
			String jsonInput = mapper.writeValueAsString(usuDto);
			return sendHttpRequest(API_BASE_URL + "/usuarios/loginGoogle", "POST", jsonInput);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public UsuarioDto obtenerUsuarioPorEmail(String email) {
        try {
            String response = sendHttpRequest(API_BASE_URL + "/usuarios/email/" + email, "GET", null);
            return response != null ? mapper.readValue(response, UsuarioDto.class) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
