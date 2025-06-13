package controladores;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.CategoriaDto;
import dtos.ProyectoDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.CategoriaService;
import servicios.ProyectosCategoriaService;

/**
 * Controlador encargado de manejar las solicitudes relacionadas con los proyectos de una categoría.
 * Ahora sólo devuelve aquellos proyectos cuyo idEstado == 2 ("Activo") y, si no se pasa parámetro,
 * carga el listado de categorías para poblar dinámicamente el desplegable en la vista.
 */
@WebServlet("/proyectosCategoria")
public class ProyectosCategoriaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProyectosCategoriaService proyectosCategoriaService = new ProyectosCategoriaService();
    private final CategoriaService categoriaService = new CategoriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idCategoriaStr = request.getParameter("id");

        // 1) Si no se especifica categoría, cargamos todas para el desplegable y mostramos la JSP
        if (idCategoriaStr == null || idCategoriaStr.isEmpty()) {
            List<CategoriaDto> categorias = categoriaService.listarCategorias();
            request.setAttribute("categorias", categorias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/proyectosCategoria.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 2) Si viene el parámetro id, devolvemos JSON de los proyectos activos de esa categoría
        try {
            Long idCategoria = Long.parseLong(idCategoriaStr);

            // Obtener todos los proyectos de la categoría
            List<ProyectoDto> todos = proyectosCategoriaService.obtenerProyectosPorCategoria(idCategoria);

            // Filtrar sólo aquellos cuyo idEstado == 2 (Activo)
            List<ProyectoDto> filtrados = todos.stream()
                .filter(p -> p.getIdEstado() != null && p.getIdEstado() == 2)
                .collect(Collectors.toList());

            // (Opcional) depuración
            filtrados.forEach(System.out::println);

            // Devolver JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
            response.getWriter().write(mapper.writeValueAsString(filtrados));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "El parámetro 'id' debe ser un número válido");
        }
    }
}
