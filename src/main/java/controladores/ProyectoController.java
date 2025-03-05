package controladores;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dtos.ProyectoDto;
import dtos.SesionDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.ApiService;

@WebServlet("/proyecto")
@MultipartConfig
public class ProyectoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ApiService apiService = new ApiService();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	HttpSession session = request.getSession();


        SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");
        System.out.println(sesionUsu.toString());
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formProyecto.jsp");
        dispatcher.forward(request, response);
	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("entramos en la crecion del proyecto");
        // Crear el objeto ProyectoDto
        ProyectoDto proyecto = new ProyectoDto();

        // Obtener la sesión para el idUsuario
        HttpSession session = request.getSession();

        SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");
        System.out.println(sesionUsu.toString());
        Long idUsuario = null;

        if (sesionUsu != null) {
            idUsuario = sesionUsu.getId();
        }
        
        System.out.println("idUsuario:" + idUsuario);
        // Asumiendo que el ID del usuario está en la sesión
        if (idUsuario == null) {
            request.setAttribute("error", "Debes iniciar sesión para crear un proyecto.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        proyecto.setIdUsuario(idUsuario);

        // Asignar valores del formulario
        proyecto.setNombreProyecto(request.getParameter("nombreProyecto"));
        proyecto.setDescripcionProyecto(request.getParameter("descripcionProyecto"));

        // Manejar imágenes como byte[]
        proyecto.setImagen1Proyecto(request.getPart("imagen1Proyecto") != null && request.getPart("imagen1Proyecto").getSize() > 0 
            ? request.getPart("imagen1Proyecto").getInputStream().readAllBytes() : null);
        proyecto.setImagen2Proyecto(request.getPart("imagen2Proyecto") != null && request.getPart("imagen2Proyecto").getSize() > 0 
            ? request.getPart("imagen2Proyecto").getInputStream().readAllBytes() : null);
        proyecto.setImagen3Proyecto(request.getPart("imagen3Proyecto") != null && request.getPart("imagen3Proyecto").getSize() > 0 
            ? request.getPart("imagen3Proyecto").getInputStream().readAllBytes() : null);

        // Fecha de inicio (actual)
        proyecto.setFechaInicioProyecto(LocalDateTime.now());

        // Fecha de finalización
        String fechaFinalizacionStr = request.getParameter("fechaFinalizacionProyecto");
        proyecto.setFechaFinalizacionProyecto(LocalDate.parse(fechaFinalizacionStr));

        // Meta de recaudación
        proyecto.setMetaRecaudacionProyecto(Double.parseDouble(request.getParameter("metaRecaudacionProyecto")));

        // Estado del proyecto (activo por defecto)
        proyecto.setEstadoProyecto(true);

        // Categoría
        proyecto.setIdCategoria(Long.parseLong(request.getParameter("categoriaProyecto")));

        System.out.println(proyecto.toString());
        // Enviar a la API
        String resultado = apiService.registroProyecto(proyecto, session);

        // Redirigir según el resultado
        if ("success".equals(resultado)) {
            response.sendRedirect("exito.jsp");
        } else {
            request.setAttribute("error", "No se pudo crear el proyecto. Inténtalo de nuevo.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}