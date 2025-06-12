package controladores;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dtos.DonacionDto;
import dtos.ProyectoDto;
import dtos.SesionDto;
import servicios.ApiService;
import servicios.DonacionService;

/**
 * ProyectoMostrarController
 *
 * Controlador que maneja la petición para mostrar un proyecto en detalle.
 * Recupera de la API (ApiBoostly) un ProyectoDto y sus imágenes (byte[])
 * convierte cada imagen a Base64 y las asigna a atributos de request. Además,
 * invoca DonacionService para recuperar todas las donaciones pendientes de
 * autorización (estado = "PENDIENTE_AUTORIZACION") para ese proyecto.
 *
 * Finalmente, envía los datos a la vista proyecto.jsp.
 */
@WebServlet("/proyectoMostrar")
public class ProyectoMostrarController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Servicio que consume la API REST de proyectos (ApiBoostly)
	private ApiService apiService = new ApiService();
	// Servicio que consume la API REST de donaciones (ApiBoostly) desde WebBoostly
	private DonacionService donacionService = new DonacionService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1) Verificar que el usuario esté en sesión
			HttpSession session = request.getSession();
			SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");
			if (sesionUsu == null) {
				// No hay sesión, redirigir a la página de login
				response.sendRedirect("login.jsp");
				return;
			}

			// 2) Obtener el parámetro "id" (ID del proyecto)
			String idProyectoParam = request.getParameter("id");
			if (idProyectoParam == null || idProyectoParam.isEmpty()) {
				// Si no llega ID, mostrar error o redirigir a una página de error genérica
				response.sendRedirect("error.jsp");
				return;
			}

			Long idProyecto = Long.parseLong(idProyectoParam);

			// 3) Llamar a la API para recuperar los datos del proyecto
			ProyectoDto proyecto = apiService.obtenerProyectoPorId(idProyecto);
			if (proyecto == null) {
				// Si la API no devolvió nada, redirigir a error
				response.sendRedirect("error.jsp");
				return;
			}

			// 4) Convertir las imágenes (byte[]) a Base64 para mostrarlas en la vista
			if (proyecto.getImagen1Proyecto() != null) {
				String imagen1Base64 = Base64.getEncoder().encodeToString(proyecto.getImagen1Proyecto());
				request.setAttribute("imagen1Base64", imagen1Base64);
			}
			if (proyecto.getImagen2Proyecto() != null) {
				String imagen2Base64 = Base64.getEncoder().encodeToString(proyecto.getImagen2Proyecto());
				request.setAttribute("imagen2Base64", imagen2Base64);
			}
			if (proyecto.getImagen3Proyecto() != null) {
				String imagen3Base64 = Base64.getEncoder().encodeToString(proyecto.getImagen3Proyecto());
				request.setAttribute("imagen3Base64", imagen3Base64);
			}

			// 5) Recuperar todas las donaciones pendientes de autorización para este
			// proyecto
			List<DonacionDto> donacionesPendientes = donacionService.obtenerDonacionesPendientes(idProyecto);
			request.setAttribute("donacionesPendientes", donacionesPendientes);

			// 6) Poner el ProyectoDto en request y despachar a la JSP
			request.setAttribute("proyecto", proyecto);

			RequestDispatcher dispatcher = request.getRequestDispatcher("proyecto.jsp");
			dispatcher.forward(request, response);

		} catch (NumberFormatException e) {
			// Si el parámetro no es un Long válido, redirigir a error
			response.sendRedirect("error.jsp");
		}
	}
}
