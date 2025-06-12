package controladores;

import java.io.IOException;
import java.util.List;

import dtos.ProyectoDto;
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

/**
 * Controlador encargado de gestionar la visualización y edición de la cuenta
 * del usuario. Este controlador maneja la solicitud GET para obtener los datos
 * del usuario y sus proyectos, y redirigir a la página de la cuenta del
 * usuario.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/cuenta") // Anotación para registrar el servlet en el mapeo de URL "/cuenta"
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Servicio para interactuar con la API y obtener los datos del usuario
	private ApiService apiService = new ApiService();

	/**
	 * Método que maneja la solicitud GET. Obtiene los datos del usuario y sus
	 * proyectos, y los pasa a la vista.
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
			// Obtener la sesión del usuario
			HttpSession session = request.getSession();
			SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");

			if (sesionUsu == null) {
				// Si no hay sesión iniciada, redirigir a la página de login
				response.sendRedirect("login.jsp");
				return;
			}

			// Obtener el ID del usuario de la sesión
			long idUsuario = sesionUsu.getId();

			// Obtener los datos del usuario y los proyectos asociados a él
			UsuarioDto usuario = apiService.obtenerUsuarioPorId(idUsuario);
			List<ProyectoDto> proyecto = apiService.obtenerProyectosPorUsuario(idUsuario);

			// Si el usuario existe, pasar los datos a la vista
			if (usuario != null) {
				System.out.println(usuario.toString());
				// Establecer los atributos para ser usados en la vista
				request.setAttribute("usuario", usuario);
				request.setAttribute("proyectos", proyecto);

				// Redirigir a la vista "usuario.jsp" para mostrar los datos
				RequestDispatcher dispatcher = request.getRequestDispatcher("usuario.jsp");
				dispatcher.forward(request, response);
			} else {
				// Si el usuario no se encuentra, mostrar un error
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
			}
		} catch (Exception e) {
			// Manejar errores y enviar un mensaje de error
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al procesar la solicitud.");
		}
	}
}
