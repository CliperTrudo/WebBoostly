package controladores;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
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
 * Ahora sólo devuelve aquellos proyectos cuyo idEstado == 2 ("Activo").
 */
@WebServlet("/proyectosCategoria")
public class ProyectosCategoriaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProyectosCategoriaService proyectosCategoriaService = new ProyectosCategoriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
        // Obtener el ID de la categoría desde la URL
        String idCategoriaStr = request.getParameter("id");

        // Si no se especifica un ID de categoría, redirige a la página de vista
        if (idCategoriaStr == null || idCategoriaStr.isEmpty()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/proyectosCategoria.jsp");
            dispatcher.forward(request, response);
            return;
        }

        
            Long idCategoria = Long.parseLong(idCategoriaStr);

            // 1) Obtener todos los proyectos de la categoría
            List<ProyectoDto> todos = proyectosCategoriaService.obtenerProyectosPorCategoria(idCategoria);

            // 2) Filtrar sólo aquellos cuyo idEstado == 2 (Activo)
            List<ProyectoDto> filtrados = todos.stream()
                .filter(p -> p.getIdEstado() != null && p.getIdEstado() == 2)
                .collect(Collectors.toList());

            // (Opcional) imprimir para depuración
            for (ProyectoDto proyectoDto : filtrados) {
                System.out.println(proyectoDto);
            }

            // 3) Devolver JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            response.getWriter().write(mapper.writeValueAsString(filtrados));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'id' debe ser un número válido");
        }
    }
}
