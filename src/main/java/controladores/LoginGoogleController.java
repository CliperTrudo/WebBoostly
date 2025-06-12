package controladores;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.oauth2.model.Userinfoplus;

import dtos.SesionDto;
import dtos.UsuarioDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.ApiService;

/**
 * Controlador que maneja la autenticación de los usuarios a través de Google
 * OAuth2. Este controlador maneja las solicitudes GET y POST para la
 * autenticación con Google, obteniendo el código de autorización, el token de
 * acceso, y la información del usuario.
 * 
 * Si el usuario no existe en la base de datos, se registra, y si ya existe, se
 * inicia sesión. Finalmente, se guarda la información del usuario en la sesión
 * y se redirige al usuario a la página principal.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/login/google") // Anotación para registrar el servlet en el mapeo de URL "/login/google"
public class LoginGoogleController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Las credenciales de la API de Google (deben ser protegidas)
	private static final String CLIENT_ID = "88643632417-723j5j8kmo55lmoqm6fr8n9pe8btn4qt.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "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";
	private static final String REDIRECT_URI = "http://localhost:8080/webboostly/login/google";

	private ApiService apiService = new ApiService(); // Servicio para interactuar con la API y la base de datos

	/**
	 * Método encargado de manejar la solicitud GET. Redirige al usuario a la página
	 * de autenticación de Google.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("datos"); // Elimina cualquier dato de sesión previo

			String code = request.getParameter("code"); // Obtener el código de autorización de la URL

			if (code != null) {
				doPost(request, response); // Si existe el código, se procede a hacer POST para obtener el token
			} else {
				try {
					// Inicia el flujo de autenticación de Google
					GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
							GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
							CLIENT_ID, CLIENT_SECRET,
							Arrays.asList(Oauth2Scopes.USERINFO_EMAIL, Oauth2Scopes.USERINFO_PROFILE))
							.setAccessType("offline").build();

					// Solicitar autorización del usuario para acceder a su cuenta
					String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI)
							.set("prompt", "select_account").build();

					response.sendRedirect(authorizationUrl); // Redirige al usuario a la URL de Google
				} catch (GeneralSecurityException | IOException e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la autenticación.");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			// si llegas aquí, hubo un fallo
			request.setAttribute("error", "se produjo un error, intentelo mas tarde");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	/**
	 * Método encargado de manejar la solicitud POST para procesar el código de
	 * autorización de Google. Obtiene el token de acceso y la información del
	 * usuario desde Google.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		String code = request.getParameter("code"); // Obtener el código de autorización

		if (code == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Código de autorización no encontrado");
			return;
		}

		
			System.out.println("Iniciando flujo de OAuth2...");

			// Inicia el flujo de autenticación de Google con el código de autorización
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), CLIENT_ID,
					CLIENT_SECRET, Arrays.asList(Oauth2Scopes.USERINFO_EMAIL, Oauth2Scopes.USERINFO_PROFILE))
					.setAccessType("offline").build();

			// Obtener el token de acceso utilizando el código de autorización
			TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
			Credential credential = flow.createAndStoreCredential(tokenResponse, "user");

			// Obtener información del usuario desde Google usando el token de acceso
			Oauth2 oauth2 = new Oauth2.Builder(GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(), credential).setApplicationName("Google OAuth2 Login").build();

			Userinfoplus userinfo = oauth2.userinfo().get().execute();
			System.out.println("Información del usuario obtenida: " + userinfo);

			// Crear un objeto UsuarioDto con los datos del usuario
			UsuarioDto usuario = new UsuarioDto();
			usuario.setMailUsuario(userinfo.getEmail()); // Usamos el email como identificador único
			usuario.setApellidosUsuario(userinfo.getFamilyName());
			usuario.setNombreUsuario(userinfo.getGivenName());
			usuario.setFechaAltaUsuario(Date.valueOf(LocalDate.now())); // Establecer fecha de alta
			usuario.setGoogleUsuario(true); // Indicar que el usuario autenticado es de Google
			usuario.setContrasenyaUsuario("google_autogenerated"); // Contraseña generada por Google

			System.out.println("Usuario antes de enviar a la API: " + usuario);

			// 🚀 Buscar el usuario por email en la base de datos
			UsuarioDto usuarioExistente = apiService.obtenerUsuarioPorEmail(userinfo.getEmail());

			if (usuarioExistente == null) {
				System.out.println("Usuario no encontrado en la base de datos, registrando...");
				apiService.registroUsuario(usuario); // Registrar nuevo usuario si no existe
			} else {
				System.out.println("Usuario ya registrado, procediendo con login...");
				usuario = usuarioExistente; // Usar los datos del usuario existente
			}

			System.out.println(usuario.toString());

			// Crear un objeto SesionDto para almacenar la sesión del usuario autenticado
			SesionDto sesionDto = new SesionDto(usuarioExistente.getId(), usuarioExistente.getMailUsuario(),
					usuarioExistente.getRol() // Asegúrate de que UsuarioDto tiene este campo
			);

			// Guardamos en la sesión HTTP el objeto SesionDto con los datos del usuario
			HttpSession session = request.getSession();
			session.setAttribute("datos", sesionDto);

			System.out.println("Usuario guardado en la sesión correctamente.");
			response.sendRedirect("/webboostly/"); // Redirige a la página principal

		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la autenticación.");
		}
	}
}
