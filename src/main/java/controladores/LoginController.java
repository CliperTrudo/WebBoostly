package controladores;

import java.io.IOException;

import dtos.SesionDto;
import dtos.UsuarioDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.ApiService;
import servicios.ContrasenyaEncryptService;

/**
 * Controlador encargado de gestionar el inicio de sesión de los usuarios. Este
 * controlador procesa tanto las solicitudes GET como POST relacionadas con el
 * login. En el caso de una solicitud GET, redirige al usuario al formulario de
 * login. En una solicitud POST, valida las credenciales ingresadas y, si son
 * correctas, redirige al usuario a la página principal de la aplicación.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/login") // Registra el servlet para manejar solicitudes a la ruta "/login"
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Servicio para interactuar con la API
	private ApiService apiService = new ApiService();

	/**
	 * Método encargado de manejar la solicitud GET para mostrar la página de login.
	 * Redirige al usuario al formulario de login.
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

		// Redirige a la página de login
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Método encargado de manejar la solicitud POST para procesar los datos de
	 * inicio de sesión. Verifica las credenciales del usuario y redirige a la
	 * página principal si son correctas.
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
			// Obtener los parámetros del formulario (correo y contraseña)
			String username = request.getParameter("email");
			String password = ContrasenyaEncryptService.encryptPassword(request.getParameter("password"));

			// Crear una nueva sesión para el usuario
			HttpSession sesion = request.getSession();

			// Obtener el usuario desde la base de datos por su correo
			UsuarioDto usuarioEncoradoDto = apiService.obtenerUsuarioPorEmail(username);

			System.out.println("Contraseña ingresada: " + request.getParameter("password"));
			System.out.println("Contraseña a comparar: " + (usuarioEncoradoDto.getContrasenyaUsuario()));

			// Verificar si la contraseña en texto plano coincide con el hash almacenado en
			// la base de datos
			boolean matches = ContrasenyaEncryptService.matches(request.getParameter("password"),
					usuarioEncoradoDto.getContrasenyaUsuario());
			System.out.println("¿La contraseña coincide? " + matches);

			String respuesta;

			// Si las contraseñas coinciden
			if (matches) {
				System.out.println("Contraseña correcta");

				// Crear un objeto SesionDto para almacenar los datos del usuario
				SesionDto sesionUsu = new SesionDto();
				sesionUsu.setId(usuarioEncoradoDto.getId());
				sesionUsu.setMailUsuario(usuarioEncoradoDto.getMailUsuario());
				sesionUsu.setRolUsuario(usuarioEncoradoDto.getRol());

				// Establecer los atributos de la sesión
				sesion.setAttribute("datos", sesionUsu);
				sesion.setAttribute("nombreUsuario", usuarioEncoradoDto.getNicknameUsuario());

				respuesta = "success"; // Login exitoso
			} else {
				// Si las contraseñas no coinciden
				respuesta = "error"; // Error en el login
			}

			System.out.println(respuesta);

			// Si el login fue exitoso, redirigir a la página principal
			if ("success".equalsIgnoreCase(respuesta)) {
				response.sendRedirect("/webboostly");
				return; // Finaliza el proceso de login y redirige

			} else {
				// Si las credenciales son incorrectas, mostrar mensaje de error
				sesion.setAttribute("error", "Credenciales incorrectas");

				// Redirigir de nuevo a la página de login
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// si llegas aquí, hubo un fallo
			request.setAttribute("error", "se produjo un error, intentelo mas tarde");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}

		return;
	}
}
