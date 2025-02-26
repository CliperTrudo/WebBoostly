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

import dtos.UsuarioDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.ApiService;

@WebServlet("/login/google") // Anotación para registrar el servlet
public class LoginGoogleController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CLIENT_ID = "797777584256-rv70sv8lpr6pvl9bbki0b029p0fse5se.apps.googleusercontent.com"; // Reemplaza
	private ApiService apiService = new ApiService();																													// con
																														// tu
																														// CLIENT_ID
	private static final String CLIENT_SECRET = "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"; // Reemplaza con tu CLIENT_SECRET
	private static final String REDIRECT_URI = "http://localhost:8080/webboostly/login/google"; // Debe coincidir con el
																								// URI de redirección en
																								// Google

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		// Verifica si el usuario ya está autenticado
		if (session.getAttribute("user") != null) {
			// Si el usuario ya está autenticado, redirige a la página de bienvenida
			response.sendRedirect("/welcome");
		} else {
			// Verifica si hay un parámetro "code" en la URL
			String code = request.getParameter("code");

			// Si el código de autorización ya está en la URL, procesa el intercambio de
			// tokens
			if (code != null) {
				
				// Llamamos al método doPost para manejar la obtención del token y el login
				doPost(request, response);
			} else {
				// Si no hay código, redirige a Google para la autenticación
				try {
					GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
							GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
							CLIENT_ID, CLIENT_SECRET,
							Arrays.asList(Oauth2Scopes.USERINFO_EMAIL, Oauth2Scopes.USERINFO_PROFILE))
							.setAccessType("offline").build();

					String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
					response.sendRedirect(authorizationUrl); // Redirige al usuario para login
				} catch (GeneralSecurityException | IOException e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							"Ocurrió un error durante la autenticación.");
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code"); // Recibe el código de Google
		System.out.println("Código recibido: " + code); // Depuración para verificar si el código se recibe
														// correctamente

		if (code != null) {
			try {
				System.out.println("Iniciando flujo de OAuth2...");
				// Crea el flujo de OAuth2 y obtiene el token de acceso
				GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
						GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), CLIENT_ID,
						CLIENT_SECRET, Arrays.asList(Oauth2Scopes.USERINFO_EMAIL, Oauth2Scopes.USERINFO_PROFILE))
						.setAccessType("offline").build();

				System.out.println("Solicitando token con el código de autorización...");
				TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
				Credential credential = flow.createAndStoreCredential(tokenResponse, "user");

				// Usamos las credenciales para obtener información del usuario
				Oauth2 oauth2 = new Oauth2.Builder(GoogleNetHttpTransport.newTrustedTransport(),
						JacksonFactory.getDefaultInstance(), credential).setApplicationName("Google OAuth2 Example")
						.build();

				System.out.println("Obteniendo información del usuario...");
				Userinfoplus userinfo = oauth2.userinfo().get().execute();
				System.out.println("Información del usuario obtenida: " + userinfo); // Depuración para ver la info del

				UsuarioDto usuario = new UsuarioDto();
				usuario.setMailUsuario(userinfo.getEmail());
				usuario.setApellidosUsuario(userinfo.getFamilyName());
				usuario.setNombreUsuario(userinfo.getGivenName());

				// Convertir LocalDate a Date
				usuario.setFechaAltaUsuario(Date.valueOf(LocalDate.now()));  // Convertir LocalDate a java.sql.Date

				usuario.setGoogleUsuario(true);
				usuario.setContrasenyaUsuario("aaaaa");
				
				System.out.println("Usuario justo antes de mandar" + usuario.toString());
				
				apiService.loginGoogle(usuario, request.getSession());
				
				// Almacenamos la información del usuario en la sesión
				HttpSession session = request.getSession();
				session.setAttribute("user", userinfo);
				System.out.println("Usuario guardado en la sesión.");

				// Redirige a una página de bienvenida
				response.sendRedirect("/welcome");

			} catch (GeneralSecurityException | IOException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error al procesar el código de autorización.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Código de autorización no encontrado");
		}
	}
}
