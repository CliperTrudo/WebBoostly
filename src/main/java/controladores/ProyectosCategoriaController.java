package controladores;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ProyectosCategoriaService;
import dtos.ProyectoDto;

/**
 * Controlador encargado de manejar las solicitudes relacionadas con los proyectos de una categoría.
 * Este controlador permite obtener los proyectos de una categoría específica y mostrarlos en formato JSON
 * o redirigir a una página de vista si no se especifica un ID de categoría.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/proyectosCategoria") // Anotación para registrar el servlet en el mapeo de URL "/proyectosCategoria"
public class ProyectosCategoriaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProyectosCategoriaService proyectosCategoriaService = new ProyectosCategoriaService(); // Servicio para obtener los proyectos por categoría

    /**
     * Método encargado de manejar la solicitud GET. Si se proporciona un ID de categoría en la URL, obtiene los proyectos
     * de esa categoría y los devuelve en formato JSON. Si no se proporciona un ID, redirige al usuario a la página de proyectos.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Hola");

        // Obtener el ID de la categoría desde la URL
        String idCategoriaStr = request.getParameter("id");

        // Si no se especifica un ID de categoría, redirige a la página de proyectos
        if (idCategoriaStr == null || idCategoriaStr.isEmpty()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/proyectosCategoria.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        try {
            // Convertir el ID de la categoría a Long
            Long idCategoria = Long.parseLong(idCategoriaStr);
            
            // Obtener los proyectos de la categoría usando el servicio
            List<ProyectoDto> proyectos = proyectosCategoriaService.obtenerProyectosPorCategoria(idCategoria);
            
            // Imprimir los proyectos para depuración
            for (ProyectoDto proyectoDto : proyectos) {
                System.out.println(proyectoDto.toString());
            }

            // Establecer la respuesta como JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Usar ObjectMapper para convertir los proyectos a formato JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Registrar el módulo para manejar fechas en formato JSON

            // Escribir la lista de proyectos en el cuerpo de la respuesta
            response.getWriter().write(mapper.writeValueAsString(proyectos));

        } catch (NumberFormatException e) {
            // Si el ID de la categoría no es un número válido, devolver un error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'id' debe ser un número válido");
        }
    }
}
