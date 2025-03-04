package controladores;

import java.io.IOException;
import java.util.Iterator;
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

@WebServlet("/proyectosCategoria")
public class ProyectosCategoriaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProyectosCategoriaService proyectosCategoriaService = new ProyectosCategoriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("hola");
        String idCategoriaStr = request.getParameter("id");

        if (idCategoriaStr == null || idCategoriaStr.isEmpty()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/proyectosCategoria.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        try {
            Long idCategoria = Long.parseLong(idCategoriaStr);
            List<ProyectoDto> proyectos = proyectosCategoriaService.obtenerProyectosPorCategoria(idCategoria);
            for (ProyectoDto proyectoDto : proyectos) {
				System.out.println(proyectoDto.toString());
			}
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            response.getWriter().write(mapper.writeValueAsString(proyectos));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'id' debe ser un número válido");
        }
    }
}