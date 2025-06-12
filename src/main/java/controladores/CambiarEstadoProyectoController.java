package controladores;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.ApiService;
import dtos.ProyectoDto;
import dtos.SesionDto;

/**
 * Servlet que recibe POST con idProyecto y nuevoEstado,
 * llama a la API para actualizar el estado del proyecto,
 * y redirige luego al panel de administración.
 */
@WebServlet("/cambiarEstadoProyecto")
public class CambiarEstadoProyectoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ApiService apiService = new ApiService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
        // 1. Verificar que haya sesión y que el usuario sea Admin (rol = 3)
        HttpSession session = request.getSession(false);
        SesionDto sesion = (session != null) ? (SesionDto) session.getAttribute("datos") : null;
        if (sesion == null || sesion.getRolUsuario() != 3L) {
            // No hay sesión o no es Admin → redirigir a login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. Obtener parámetros idProyecto y nuevoEstado
        String idProyectoStr = request.getParameter("idProyecto");
        String nuevoEstadoStr = request.getParameter("nuevoEstado");

        if (idProyectoStr != null && !idProyectoStr.isEmpty()
                && nuevoEstadoStr != null && !nuevoEstadoStr.isEmpty()) {
            try {
                Long idProyecto = Long.parseLong(idProyectoStr);
                Integer nuevoEstado = Integer.parseInt(nuevoEstadoStr);

                // 3. Obtener el ProyectoDto actual desde la API
                ProyectoDto proyecto = apiService.obtenerProyectoPorId(idProyecto);
                if (proyecto != null) {
                    // 4. Cambiar solo el campo idEstado
                    proyecto.setIdEstado(Long.valueOf(nuevoEstado));

                    // 5. Llamar a la API para actualizar (PUT)
                    apiService.actualizarProyecto(proyecto);
                }
            } catch (NumberFormatException e) {
                // ID no válido, ignorar o loguear
            }
        }

        // 6. Redirigir de vuelta a /admin para recargar el panel
        response.sendRedirect(request.getContextPath() + "/admin");
    	} catch (Exception e) {
            e.printStackTrace();
        // si llegas aquí, hubo un fallo
        request.setAttribute("error", "se produjo un error, intentelo mas tarde");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
