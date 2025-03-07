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

@WebServlet("/proyectoMostrar")
public class ProyectoMostrarController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ApiService apiService = new ApiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idProyectoParam = request.getParameter("id");
        if (idProyectoParam == null || idProyectoParam.isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }
        
        try {
            Long idProyecto = Long.parseLong(idProyectoParam);
            ProyectoDto proyecto = apiService.obtenerProyectoPorId(idProyecto);
            
            if (proyecto != null) {
                // Convertir imágenes a Base64 solo si no están vacías
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

                request.setAttribute("proyecto", proyecto);
                RequestDispatcher dispatcher = request.getRequestDispatcher("proyecto.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp");
        }
    }
}
