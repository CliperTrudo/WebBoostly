package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.MailContrasenyaRequestDto;
import dtos.ProyectoDto;
import dtos.SesionDto;
import dtos.UsuarioDto;
import jakarta.servlet.http.HttpSession;

public class ApiService {

	private static final String API_BASE_URL = "http://localhost:8081/apiBoostly/api/usuarios";

	// Configuración del ObjectMapper para soportar Java 8 Date/Time
	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	public List<ProyectoDto> obtenerProyectosPorUsuario(Long idUsuario) {
        String url = "http://localhost:8081/apiBoostly/api/proyectos/usuario/" + idUsuario;

        try {
            URI uri = new URI(url);
            URL apiUrl = uri.toURL();

            HttpURLConnection conexion = (HttpURLConnection) apiUrl.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("Accept", "application/json");

            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return Arrays.asList(mapper.readValue(response.toString(), ProyectoDto[].class));
            } else {
                System.out.println("Error al obtener proyectos del usuario: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public UsuarioDto obtenerUsuarioPorId(Long idUsuario) {
        String url = API_BASE_URL + "/" + idUsuario;

        try {
            URI uri = new URI(url);
            URL apiUrl = uri.toURL();

            HttpURLConnection conexion = (HttpURLConnection) apiUrl.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("Accept", "application/json");

            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return mapper.readValue(response.toString(), UsuarioDto.class);
            } else {
                System.out.println("Error al obtener usuario: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public UsuarioDto sendLoginData(MailContrasenyaRequestDto loginRequest, HttpSession session) {
		String url = API_BASE_URL + "/login";
		String jsonInput;
		try {
			jsonInput = mapper.writeValueAsString(loginRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		 UsuarioDto usuario = sendLoguin(url, jsonInput);
		 
		 return usuario;
		
	}
	
	public String registroProyecto(ProyectoDto proyectoDto, HttpSession session) {
	    String url = "http://localhost:8081/apiBoostly/api" + "/proyectos"; // Ajusta según tu endpoint real
	    String jsonInput;
	    try {
	        jsonInput = mapper.writeValueAsString(proyectoDto);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "error";
	    }
	    return sendPostRequest(url, jsonInput, session);
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
		System.out.println("Entramos vamos ");
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
