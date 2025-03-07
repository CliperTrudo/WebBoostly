package filtros;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro que intercepta las solicitudes hacia cualquier archivo con extensión .jsp.
 * Si un usuario intenta acceder directamente a un archivo JSP, será redirigido a la página principal o a la página de login.
 * Este filtro ayuda a evitar el acceso directo a las páginas JSP sin pasar por el flujo adecuado de controladores o autenticación.
 */
@WebFilter("*.jsp") // Bloquea el acceso directo a todos los archivos .jsp
public class JspFiltroAcceso implements Filter {

    /**
     * Método que intercepta la solicitud y valida si el usuario está intentando acceder a un archivo .jsp directamente.
     * Si lo está haciendo, redirige a la página principal. De lo contrario, permite que la solicitud continúe su flujo normal.
     * 
     * @param request La solicitud del usuario.
     * @param response La respuesta del servidor.
     * @param chain El objeto que permite pasar la solicitud al siguiente filtro o al servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error durante el procesamiento del servlet.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Convertir la solicitud y respuesta a tipos específicos para HttpServlet
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Obtener la URI de la solicitud
        String uri = req.getRequestURI();

        // Si la URI termina con ".jsp", significa que el usuario está intentando acceder a una página JSP directamente
        if (uri.endsWith(".jsp")) {
            // Redirigir al usuario a la página principal
            res.sendRedirect(req.getContextPath() + "/"); // Cambia "/login" por la vista deseada
        } else {
            // Permitir que la solicitud continúe normalmente
            chain.doFilter(request, response);
        }
    }

    /**
     * Método de inicialización del filtro. Se ejecuta una vez al inicio del ciclo de vida del filtro.
     * Aquí puedes agregar cualquier configuración inicial si es necesario, pero en este caso no es utilizado.
     * 
     * @param filterConfig La configuración del filtro.
     * @throws ServletException Si ocurre un error durante la inicialización del filtro.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización si es necesaria
    }

    /**
     * Método de destrucción del filtro. Se ejecuta al finalizar el ciclo de vida del filtro.
     * Aquí puedes realizar tareas de limpieza si es necesario.
     */
    @Override
    public void destroy() {
        // Cleanup si es necesario
    }
}
