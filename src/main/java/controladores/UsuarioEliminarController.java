package controladores;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ApiService;

/**
 * Servlet que elimina un usuario invocando al endpoint DELETE /api/usuarios/{id}.
 * Mapea la URL /admin/borrarUsuario?id={idUsuario}.
 */
@WebServlet("/admin/borrarUsuario")
public class UsuarioEliminarController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Reutilizamos tu ApiService para hacer la llamada HTTP al back-end
    private final ApiService apiService = new ApiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1) Leer parámetro idUsuario
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            // Si no viene id, redirigimos de vuelta al panel de admin
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        try {
            Long idUsuario = Long.parseLong(idStr);

            // 2) Invocar al servicio que hará DELETE /api/usuarios/{id}
            boolean eliminado = apiService.eliminarUsuario(idUsuario);

            // 3) Redirigir de vuelta al panel de admin (quizá mostrando un mensaje en session/flash)
            response.sendRedirect(request.getContextPath() + "/admin");

        } catch (NumberFormatException e) {
            // ID inválido, volvemos al panel de admin
            response.sendRedirect(request.getContextPath() + "/admin");
        }
    }

    // Si prefieres procesar por POST en lugar de GET, puedes añadir doPost igual a doGet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
