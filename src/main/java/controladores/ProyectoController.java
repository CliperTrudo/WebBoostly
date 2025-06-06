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

/**
 * Controlador encargado de gestionar los proyectos de los usuarios.
 * Este controlador maneja las solicitudes GET y POST relacionadas con los proyectos.
 * En la solicitud GET, redirige al formulario donde el usuario puede crear un nuevo proyecto.
 * En la solicitud POST, procesa los datos del proyecto y lo guarda en la base de datos.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/proyecto") // Anotación para registrar el servlet en el mapeo de URL "/proyecto"
@MultipartConfig // Habilita la carga de archivos en la solicitud
public class ProyectoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ApiService apiService = new ApiService(); // Servicio para interactuar con la API y la base de datos

    /**
     * Método encargado de manejar la solicitud GET. Redirige al usuario al formulario para crear un proyecto.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");
        
        // Verificar si el usuario ha iniciado sesión
        if (sesionUsu != null) {
            System.out.println(sesionUsu.toString());
        } else {
            System.out.println("Sesión no iniciada.");
            response.sendRedirect("/webboostly/login"); // Redirige al login si no hay sesión
            return;
        }
        
        // Redirige al formulario para crear un nuevo proyecto
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formProyecto.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Método encargado de manejar la solicitud POST. Recibe los datos del formulario para crear un nuevo proyecto.
     * Valida la sesión del usuario, obtiene los datos del formulario y los envía a la API para ser registrados.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Entramos en la creación del proyecto");

        // 1️⃣ Crear el objeto ProyectoDto y completar sus campos
        ProyectoDto proyecto = new ProyectoDto();

        HttpSession session = request.getSession();
        SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");
        if (sesionUsu == null || sesionUsu.getId() == null) {
            // No hay sesión válida → volvemos al login
            System.out.println("Sesión no iniciada.");
            response.sendRedirect("/webboostly/login");
            return;
        }
        Long idUsuario = sesionUsu.getId();
        proyecto.setIdUsuario(idUsuario);

        proyecto.setNombreProyecto(request.getParameter("nombreProyecto"));
        proyecto.setDescripcionProyecto(request.getParameter("descripcionProyecto"));
        proyecto.setImagen1Proyecto(
            request.getPart("imagen1Proyecto") != null && request.getPart("imagen1Proyecto").getSize() > 0
                ? request.getPart("imagen1Proyecto").getInputStream().readAllBytes()
                : null
        );
        proyecto.setImagen2Proyecto(
            request.getPart("imagen2Proyecto") != null && request.getPart("imagen2Proyecto").getSize() > 0
                ? request.getPart("imagen2Proyecto").getInputStream().readAllBytes()
                : null
        );
        proyecto.setImagen3Proyecto(
            request.getPart("imagen3Proyecto") != null && request.getPart("imagen3Proyecto").getSize() > 0
                ? request.getPart("imagen3Proyecto").getInputStream().readAllBytes()
                : null
        );

        proyecto.setFechaInicioProyecto(LocalDateTime.now());

        String fechaFinalizacionStr = request.getParameter("fechaFinalizacionProyecto");
        proyecto.setFechaFinalizacionProyecto(LocalDate.parse(fechaFinalizacionStr));

        proyecto.setMetaRecaudacionProyecto(
            Double.parseDouble(request.getParameter("metaRecaudacionProyecto"))
        );

        // Fijas el estado por defecto (activo = 2)
        proyecto.setIdEstado(Long.valueOf(2));

        proyecto.setIdCategoria(Long.parseLong(request.getParameter("categoriaProyecto")));

        System.out.println("DTO enviado a API: " + proyecto.toString());

        // 2️⃣ Llamar al servicio para registrar el proyecto
        ProyectoDto proyectoResultado = apiService.registroProyecto(proyecto);

        // 3️⃣ Comprobar que no sea null antes de usar toString() o acceder a sus campos
        if (proyectoResultado != null && proyectoResultado.getIdProyecto() != null) {
            // Si todo fue bien, redirigimos a la página de mostrar proyecto
            System.out.println("Proyecto creado con éxito: " + proyectoResultado.toString());
            response.sendRedirect("proyectoMostrar?id=" + proyectoResultado.getIdProyecto());
        } else {
            // Si devuelve null, significa que algo falló en la API o no se devolvió un objeto válido
            System.out.println("ERROR: registroProyecto devolvió null.");
            request.setAttribute("error", "No se pudo crear el proyecto. Inténtalo de nuevo.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
