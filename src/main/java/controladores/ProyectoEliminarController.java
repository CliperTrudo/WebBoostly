package controladores;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ApiService;

@WebServlet("/eliminarProyecto")
public class ProyectoEliminarController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApiService apiService = new ApiService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Long idProyecto = Long.parseLong(request.getParameter("idProyecto"));
		
		boolean respuesta = apiService.eliminarProyecto(idProyecto);
		
		if (respuesta) {
			response.sendRedirect("/webboostly/cuenta?mensaje=Proyecto eliminado correctamente");
		}else {
			response.sendRedirect("/webboostly/proyectoMostrar?id="+idProyecto);
		}

		
	}
}
