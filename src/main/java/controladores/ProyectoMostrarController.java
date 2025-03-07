package controladores;

import java.io.IOException;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ApiService;
import dtos.ProyectoDto;

/**
 * Controlador encargado de mostrar los detalles de un proyecto.
 * Este controlador maneja la solicitud GET para obtener los datos de un proyecto por su ID y mostrarlos al usuario.
 * Si el proyecto existe, también convierte las imágenes del proyecto a formato Base64 para ser mostradas en la vista.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/proyectoMostrar") // Anotación para registrar el servlet en el mapeo de URL "/proyectoMostrar"
public class ProyectoMostrarController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ApiService apiService = new ApiService(); // Servicio para interactuar con la API y la base de datos

    /**
     * Método encargado de manejar la solicitud GET. Obtiene el proyecto por su ID y redirige a la vista correspondiente.
     * Si el proyecto tiene imágenes, las convierte a Base64 para que puedan ser mostradas en la vista.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener el ID del proyecto desde la URL
        String idProyectoParam = request.getParameter("id");
        if (idProyectoParam == null || idProyectoParam.isEmpty()) {
            response.sendRedirect("error.jsp"); // Redirige a la página de error si el ID no es válido
            return;
        }
        
        try {
            Long idProyecto = Long.parseLong(idProyectoParam); // Convertir el ID a Long
            ProyectoDto proyecto = apiService.obtenerProyectoPorId(idProyecto); // Obtener el proyecto desde la base de datos
            
            if (proyecto != null) {
                // Convertir las imágenes a Base64 si están presentes
                if (proyecto.getImagen1Proyecto() != null) {
                    String imagen1Base64 = Base64.getEncoder().encodeToString(proyecto.getImagen1Proyecto());
                    request.setAttribute("imagen1Base64", imagen1Base64); // Añadir la imagen al request
                }
                if (proyecto.getImagen2Proyecto() != null) {
                    String imagen2Base64 = Base64.getEncoder().encodeToString(proyecto.getImagen2Proyecto());
                    request.setAttribute("imagen2Base64", imagen2Base64); // Añadir la imagen al request
                }
                if (proyecto.getImagen3Proyecto() != null) {
                    String imagen3Base64 = Base64.getEncoder().encodeToString(proyecto.getImagen3Proyecto());
                    request.setAttribute("imagen3Base64", imagen3Base64); // Añadir la imagen al request
                }

                // Añadir el proyecto al request y redirigir a la vista
                request.setAttribute("proyecto", proyecto);
                RequestDispatcher dispatcher = request.getRequestDispatcher("proyecto.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("error.jsp"); // Redirige a la página de error si el proyecto no se encuentra
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp"); // Redirige a la página de error si hay un problema con el formato del ID
        }
    }
}
