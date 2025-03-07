package filtros;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("*.jsp") // Bloquea el acceso directo a todos los archivos .jsp
public class JspFiltroAcceso implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // Si el usuario intenta acceder a un archivo JSP directamente, lo redirige al login o página principal
        if (uri.endsWith(".jsp")) {
            res.sendRedirect(req.getContextPath() + "/"); // Cambia "/login" por la vista deseada
        } else {
            chain.doFilter(request, response); // Permite continuar con la solicitud normal
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización si es necesaria
    }

    @Override
    public void destroy() {
        // Cleanup si es necesario
    }
}

