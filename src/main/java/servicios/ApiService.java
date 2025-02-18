package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.MailContrasenyaRequestDto;
import dtos.SesionDto;
import dtos.UsuarioDto;
import jakarta.servlet.http.HttpSession;

public class ApiService {

	private static final String API_BASE_URL = "http://localhost:8081/apiBoostly/api/usuarios";

	// Configuración del ObjectMapper para soportar Java 8 Date/Time
	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	public String sendLoginData(MailContrasenyaRequestDto loginRequest, HttpSession session) {
		String url = API_BASE_URL + "/login";
		String jsonInput;
		try {
			jsonInput = mapper.writeValueAsString(loginRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		UsuarioDto usuario = sendLoguin(url, jsonInput);

		System.out.println("Contraseña ingresada: " + loginRequest.getContrasenya());
		System.out.println("Contraseña a comparar: " + (usuario.getContrasenyaUsuario()));

		// Verificar si la contraseña en texto plano coincide con el hash
		boolean matches = ContrasenyaEncryptService.matches(loginRequest.getContrasenya(),
				usuario.getContrasenyaUsuario());
		System.out.println("¿La contraseña coincide? " + matches);

		if (matches) {
			System.out.println("Contraseña correcta");
			SesionDto sesionUsu = new SesionDto();
			sesionUsu.setId(usuario.getId());
			sesionUsu.setMailUsuario(usuario.getMailUsuario());
			sesionUsu.setRolUsuario(usuario.getRolUsuario());
			session.setAttribute("datos", sesionUsu);
			return "success";
		} else {
			// La contraseña es incorrecta
			return "error";
		}

	}

	public String loginGoogle(UsuarioDto usuDto, HttpSession session) {
		String url = API_BASE_URL + "/loginGoogle";
		String jsonInput;
		try {
			jsonInput = mapper.writeValueAsString(usuDto);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return sendPostRequest(url, jsonInput, session);
	}

	public UsuarioDto encript(UsuarioDto usuDto, HttpSession session) {

		String url = API_BASE_URL;
		String jsonInput;
		
		try {
			jsonInput = mapper.writeValueAsString(usuDto);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sendLoguin(url, jsonInput);
	}

	public String registroUsuario(UsuarioDto usuDto, HttpSession session) {

		String url = API_BASE_URL;
		String jsonInput;
		try {
			jsonInput = mapper.writeValueAsString(usuDto);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return sendPostRequest(url, jsonInput, session);
	}

	private String sendPostRequest(String url, String jsonInput, HttpSession session) {
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

				SesionDto sesionDto = mapper.readValue(response.toString(), SesionDto.class);
				session.setAttribute("datos", sesionDto);

				return "success";
			} else {
				System.out.println("Error en la conexión: " + codigoRespuesta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	private UsuarioDto sendLoguin(String url, String jsonInput) {
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

				UsuarioDto usuario = mapper.readValue(response.toString(), UsuarioDto.class);

				System.out.println("Se encontro el usuario en la base de datos: " + usuario.toString());

				return usuario;
			} else {
				System.out.println("No se encontro el usuario en la base de datos");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
