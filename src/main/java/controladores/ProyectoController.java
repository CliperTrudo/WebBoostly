package controladores;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import servicios.ApiService;
import servicios.CategoriaService;

@WebServlet("/proyecto")
@MultipartConfig
public class ProyectoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ApiService apiService = new ApiService();
    private CategoriaService categoriaService = new CategoriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");

            if (sesionUsu == null) {
                response.sendRedirect("/webboostly/login");
                return;
            }

            // ➊ — Cargar las categorías para el dropdown
            List<CategoriaDto> categorias = categoriaService.listarCategorias();
            request.setAttribute("categorias", categorias);

            // Redirigir al formulario para crear un nuevo proyecto
            RequestDispatcher dispatcher = request.getRequestDispatcher("/formProyecto.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Se produjo un error, inténtelo más tarde");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("Entramos en la creación del proyecto");

            // 1️⃣ Crear el objeto ProyectoDto y completar sus campos
            ProyectoDto proyecto = new ProyectoDto();

            HttpSession session = request.getSession();
            SesionDto sesionUsu = (SesionDto) session.getAttribute("datos");
            if (sesionUsu == null || sesionUsu.getId() == null) {
                response.sendRedirect("/webboostly/login");
                return;
            }
            proyecto.setIdUsuario(sesionUsu.getId());

            proyecto.setNombreProyecto(request.getParameter("nombreProyecto"));
            proyecto.setDescripcionProyecto(request.getParameter("descripcionProyecto"));
            proyecto.setImagen1Proyecto(
                request.getPart("imagen1Proyecto") != null && request.getPart("imagen1Proyecto").getSize() > 0
                    ? request.getPart("imagen1Proyecto").getInputStream().readAllBytes()
                    : null);
            proyecto.setImagen2Proyecto(
                request.getPart("imagen2Proyecto") != null && request.getPart("imagen2Proyecto").getSize() > 0
                    ? request.getPart("imagen2Proyecto").getInputStream().readAllBytes()
                    : null);
            proyecto.setImagen3Proyecto(
                request.getPart("imagen3Proyecto") != null && request.getPart("imagen3Proyecto").getSize() > 0
                    ? request.getPart("imagen3Proyecto").getInputStream().readAllBytes()
                    : null);

            proyecto.setFechaInicioProyecto(LocalDateTime.now());
            proyecto.setFechaFinalizacionProyecto(LocalDate.parse(request.getParameter("fechaFinalizacionProyecto")));
            proyecto.setMetaRecaudacionProyecto(Double.parseDouble(request.getParameter("metaRecaudacionProyecto")));

            proyecto.setIdEstado(Long.valueOf(2));
            proyecto.setIdCategoria(Long.parseLong(request.getParameter("categoriaProyecto")));

            System.out.println("DTO enviado a API: " + proyecto);

            // 2️⃣ Llamar al servicio para registrar el proyecto
            ProyectoDto proyectoResultado = apiService.registroProyecto(proyecto);

            if (proyectoResultado != null && proyectoResultado.getIdProyecto() != null) {
                System.out.println("Proyecto creado con éxito: " + proyectoResultado);
                response.sendRedirect("proyectoMostrar?id=" + proyectoResultado.getIdProyecto());
            } else {
                System.out.println("ERROR: registroProyecto devolvió null.");
                request.setAttribute("error", "No se pudo crear el proyecto. Inténtalo de nuevo.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Se produjo un error, inténtelo más tarde");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
