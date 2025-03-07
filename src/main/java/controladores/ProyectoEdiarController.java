package controladores;

import java.io.IOException;
import java.time.LocalDate;

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
import jakarta.servlet.http.Part;
import servicios.ApiService;

/**
 * Controlador encargado de gestionar la edición de proyectos.
 * Este controlador maneja las solicitudes GET y POST relacionadas con la edición de proyectos.
 * En la solicitud GET, redirige al formulario de edición si el proyecto existe y el usuario está autenticado.
 * En la solicitud POST, procesa los datos editados del proyecto y los actualiza en la base de datos.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/editarProyecto") // Anotación para registrar el servlet en el mapeo de URL "/editarProyecto"
@MultipartConfig // Habilita la carga de archivos en la solicitud
public class ProyectoEdiarController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ApiService apiService = new ApiService(); // Servicio para interactuar con la API y la base de datos

    /**
     * Método encargado de manejar la solicitud GET. Redirige al usuario al formulario de edición de proyecto
     * si el proyecto existe y pertenece al usuario autenticado.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Hola");

        // Obtener la sesión del usuario
        HttpSession session = request.getSession();
        SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");

        // Verificar si el usuario está autenticado
        if (sesionUsu != null) {
            System.out.println(sesionUsu.toString());
        } else {
            System.out.println("Sesión no iniciada.");
            response.sendRedirect("/webboostly/login"); // Redirige al login si la sesión no está iniciada
            return;
        }

        // Obtener el ID del proyecto desde la URL
        String idProyectoParam = request.getParameter("id");

        // Si no se encuentra el ID del proyecto, redirige al perfil de usuario
        if (idProyectoParam == null || idProyectoParam.isEmpty()) {
            response.sendRedirect("/webboostly/cuenta");
            return;
        }

        try {
            Long idProyecto = Long.parseLong(idProyectoParam);
            ProyectoDto proyecto = apiService.obtenerProyectoPorId(idProyecto); // Obtener el proyecto desde la base de datos

            // Verificar si el proyecto existe y pertenece al usuario autenticado
            if (proyecto != null && proyecto.getIdUsuario().equals(sesionUsu.getId())) {
                System.out.println(proyecto.toString());
                request.setAttribute("proyecto", proyecto);
                RequestDispatcher dispatcher = request.getRequestDispatcher("./editarProyecto.jsp");
                dispatcher.forward(request, response);
                return;
            } else {
                response.sendRedirect("/webboostly/cuenta"); // Redirige si el proyecto no pertenece al usuario
                return;
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("/webboostly/cuenta"); // Redirige si hay un error al procesar el ID del proyecto
            return;
        }
    }

    /**
     * Método encargado de manejar la solicitud POST. Procesa los datos editados del proyecto y los actualiza en la base de datos.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Edición de proyecto");

        try {
            // 1️⃣ Obtener los parámetros enviados desde el formulario
            Long idProyecto = Long.parseLong(request.getParameter("idProyecto"));
            String nombreProyecto = request.getParameter("nombreProyecto");
            String descripcionProyecto = request.getParameter("descripcionProyecto");
            LocalDate fechaFinalizacionProyecto = LocalDate.parse(request.getParameter("fechaFinalizacionProyecto"));
            Double metaRecaudacionProyecto = Double.parseDouble(request.getParameter("metaRecaudacionProyecto"));
            Long idCategoria = Long.parseLong(request.getParameter("categoriaProyecto"));
            System.out.println("idProyecto" + idProyecto + "idCategoria:" + idCategoria);

            // 2️⃣ Obtener las imágenes (pueden ser opcionales)
            Part imagen1Part = request.getPart("imagen1Proyecto");
            Part imagen2Part = request.getPart("imagen2Proyecto");
            Part imagen3Part = request.getPart("imagen3Proyecto");

            byte[] imagen1Proyecto = (imagen1Part != null && imagen1Part.getSize() > 0)
                    ? imagen1Part.getInputStream().readAllBytes()
                    : null;
            byte[] imagen2Proyecto = (imagen2Part != null && imagen2Part.getSize() > 0)
                    ? imagen2Part.getInputStream().readAllBytes()
                    : null;
            byte[] imagen3Proyecto = (imagen3Part != null && imagen3Part.getSize() > 0)
                    ? imagen3Part.getInputStream().readAllBytes()
                    : null;

            // 3️⃣ Crear un objeto ProyectoDto con los datos obtenidos
            ProyectoDto proyecto = new ProyectoDto();
            proyecto.setIdProyecto(idProyecto);
            proyecto.setNombreProyecto(nombreProyecto);
            proyecto.setDescripcionProyecto(descripcionProyecto);
            proyecto.setFechaFinalizacionProyecto(fechaFinalizacionProyecto);
            proyecto.setMetaRecaudacionProyecto(metaRecaudacionProyecto);
            proyecto.setIdCategoria(idCategoria);

            // Solo actualiza imágenes si el usuario las subió
            if (imagen1Proyecto != null) proyecto.setImagen1Proyecto(imagen1Proyecto);
            if (imagen2Proyecto != null) proyecto.setImagen2Proyecto(imagen2Proyecto);
            if (imagen3Proyecto != null) proyecto.setImagen3Proyecto(imagen3Proyecto);

            System.out.println(proyecto.toString());

            // 4️⃣ Llamar al servicio para actualizar en la base de datos
            boolean actualizado = apiService.actualizarProyecto(proyecto);

            // 5️⃣ Redirigir según el resultado de la actualización
            if (actualizado) {
                response.sendRedirect("/webboostly/proyectoMostrar?id=" + idProyecto); // Redirige al proyecto si la actualización fue exitosa
            } else {
                response.sendRedirect("/webboostly/editarProyecto?id=" + idProyecto + "&error=No se pudo actualizar"); // Si hubo un error
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/webboostly/editarProyecto?error=Error al procesar los datos"); // Error al procesar la solicitud
        }
    }
}
