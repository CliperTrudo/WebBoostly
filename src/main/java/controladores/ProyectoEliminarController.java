package controladores;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ApiService;

/**
 * Controlador encargado de gestionar la eliminación de proyectos. Este
 * controlador maneja la solicitud POST para eliminar un proyecto de la base de
 * datos.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/eliminarProyecto") // Anotación para registrar el servlet en el mapeo de URL "/eliminarProyecto"
public class ProyectoEliminarController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ApiService apiService = new ApiService(); // Servicio para interactuar con la API y la base de datos

	/**
	 * Método encargado de manejar la solicitud POST para eliminar un proyecto.
	 * Recibe el ID del proyecto a eliminar y lo elimina de la base de datos.
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
			// Obtener el ID del proyecto desde el formulario
			Long idProyecto = Long.parseLong(request.getParameter("idProyecto"));

			// Llamar al servicio para eliminar el proyecto
			boolean respuesta = apiService.eliminarProyecto(idProyecto);

			// Redirigir según el resultado de la eliminación
			if (respuesta) {
				response.sendRedirect("/webboostly/cuenta?mensaje=Proyecto eliminado correctamente"); // Redirigir con
																										// mensaje de
																										// éxito
			} else {
				response.sendRedirect("/webboostly/proyectoMostrar?id=" + idProyecto); // Redirigir al proyecto si hubo
																						// un error
			}
		} catch (Exception e) {
			e.printStackTrace();
			// si llegas aquí, hubo un fallo
			request.setAttribute("error", "se produjo un error, intentelo mas tarde");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}
}
