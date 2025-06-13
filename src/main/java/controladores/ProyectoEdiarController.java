package controladores;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import dtos.CategoriaDto;
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
import servicios.CategoriaService;

@WebServlet("/editarProyecto")
@MultipartConfig
public class ProyectoEdiarController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ApiService apiService = new ApiService();
    private CategoriaService categoriaService = new CategoriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener la sesión del usuario
            HttpSession session = request.getSession();
            SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");

            if (sesionUsu == null) {
                response.sendRedirect("/webboostly/login");
                return;
            }

            // ➊ — Cargar también aquí las categorías para el dropdown
            List<CategoriaDto> categorias = categoriaService.listarCategorias();
            request.setAttribute("categorias", categorias);

            // Obtener el ID del proyecto
            String idProyectoParam = request.getParameter("id");
            if (idProyectoParam == null || idProyectoParam.isEmpty()) {
                response.sendRedirect("/webboostly/cuenta");
                return;
            }

            Long idProyecto = Long.parseLong(idProyectoParam);
            ProyectoDto proyecto = apiService.obtenerProyectoPorId(idProyecto);

            if (proyecto != null
                    && (proyecto.getIdUsuario().equals(sesionUsu.getId())
                        || sesionUsu.getRolUsuario() == 3)) {

                request.setAttribute("proyecto", proyecto);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/editarProyecto.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("/webboostly/cuenta");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("/webboostly/cuenta");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long idProyecto = Long.parseLong(request.getParameter("idProyecto"));
            String nombreProyecto = request.getParameter("nombreProyecto");
            String descripcionProyecto = request.getParameter("descripcionProyecto");
            LocalDate fechaFinalizacionProyecto = LocalDate.parse(request.getParameter("fechaFinalizacionProyecto"));
            Double metaRecaudacionProyecto = Double.parseDouble(request.getParameter("metaRecaudacionProyecto"));
            Long idCategoria = Long.parseLong(request.getParameter("categoriaProyecto"));

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

            ProyectoDto proyecto = new ProyectoDto();
            proyecto.setIdProyecto(idProyecto);
            proyecto.setNombreProyecto(nombreProyecto);
            proyecto.setDescripcionProyecto(descripcionProyecto);
            proyecto.setFechaFinalizacionProyecto(fechaFinalizacionProyecto);
            proyecto.setMetaRecaudacionProyecto(metaRecaudacionProyecto);
            proyecto.setIdCategoria(idCategoria);

            if (imagen1Proyecto != null) proyecto.setImagen1Proyecto(imagen1Proyecto);
            if (imagen2Proyecto != null) proyecto.setImagen2Proyecto(imagen2Proyecto);
            if (imagen3Proyecto != null) proyecto.setImagen3Proyecto(imagen3Proyecto);

            boolean actualizado = apiService.actualizarProyecto(proyecto);

            if (actualizado) {
                response.sendRedirect("/webboostly/proyectoMostrar?id=" + idProyecto);
            } else {
                response.sendRedirect("/webboostly/editarProyecto?id=" + idProyecto + "&error=No se pudo actualizar");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/webboostly/editarProyecto?error=Error al procesar los datos");
        }
    }
}
