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

@WebServlet("/cuenta")
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ApiService apiService = new ApiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");
        
        if (sesionUsu == null) {
            // Si no hay sesión, redirigir a login.jsp
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            long idUsuario = sesionUsu.getId();
            UsuarioDto usuario = apiService.obtenerUsuarioPorId(idUsuario);
            List<ProyectoDto> proyecto = apiService.obtenerProyectosPorUsuario(idUsuario);
            
            
            if (usuario != null) {
            	System.out.println(usuario.toString());
                // Pasar datos del usuario a la vista
                request.setAttribute("usuario", usuario);
                request.setAttribute("proyectos", proyecto);
                RequestDispatcher dispatcher = request.getRequestDispatcher("usuario.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al procesar la solicitud.");
        }
    }
}
