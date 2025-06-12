package controladores;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dtos.UsuarioDto;
import servicios.ApiService;
import servicios.ContrasenyaEncryptService;

/**
 * Controlador para dar de alta usuarios desde la zona Admin,
 * sin comprobar/verificar correo.
 */
@WebServlet("/admin/crearUsuario")
public class AdminUsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Servicio para registrar usuario en la API
    private ApiService apiService = new ApiService();

    /**
     * GET: muestra el formulario de alta de usuario.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // simplemente forwards a la JSP de administración
        RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
        rd.forward(request, response);
    }

    /**
     * POST: procesa el formulario de alta y llama a la API para crear el usuario.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
        // 1) Construir el DTO con los parámetros del formulario
        UsuarioDto usuario = new UsuarioDto();
        usuario.setNombreUsuario(request.getParameter("nombre_usuario"));
        usuario.setApellidosUsuario(request.getParameter("apellidos_usuario"));
        usuario.setMailUsuario(request.getParameter("mail_usuario"));

        // parsear fecha_nacimiento_usuario (yyyy-MM-dd)
        String fechaNacStr = request.getParameter("fecha_nacimiento_usuario");
        if (fechaNacStr != null && !fechaNacStr.isBlank()) {
            LocalDate ld = LocalDate.parse(fechaNacStr);
            usuario.setFechaNacimientoUsuario(Date.valueOf(ld));
        }

        usuario.setNicknameUsuario(request.getParameter("nickname_usuario"));

        // encriptar contraseña
        String rawPass = request.getParameter("contrasenya_usuario");
        String encPass = ContrasenyaEncryptService.encryptPassword(rawPass);
        usuario.setContrasenyaUsuario(encPass);

        // fecha alta = hoy
        usuario.setFechaAltaUsuario(Date.valueOf(LocalDate.now()));

        // resto de campos (puedes adaptarlos según tu formulario admin)
        usuario.setDescripcionUsuario(request.getParameter("descripcion_usuario"));
        usuario.setDniUsuario(request.getParameter("dni_usuario"));
        usuario.setTelefonoUsuario(request.getParameter("telefono_usuario"));
        usuario.setImgUsuario(null);             // si no hay imagen
        usuario.setRol(Long.parseLong(request.getParameter("rol")));  // viene del select admin
        usuario.setGoogleUsuario(false);
        usuario.setTokenRecuperacion(null);
        usuario.setTokenExpiracion(null);

        // 2) Invocar al servicio de API para crear el usuario
        String jsonResult = apiService.registroUsuario(usuario);
        
    	
        // parsear JSON
        
            JsonNode resultNode = new ObjectMapper().readTree(jsonResult);
            if (resultNode.has("id")) {
                // registro OK
                response.sendRedirect(request.getContextPath() + "/admin");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        // si llegas aquí, hubo un fallo
        request.setAttribute("error", "No se pudo crear el usuario.");
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
        }

        
    
    }
}
