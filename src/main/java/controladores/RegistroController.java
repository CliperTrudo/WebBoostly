package controladores;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

import dtos.UsuarioDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import servicios.ContrasenyaEncryptService;
import servicios.EmailService; // Nueva clase para enviar correos

/**
 * Controlador encargado de gestionar el registro de nuevos usuarios.
 * Este controlador maneja la solicitud GET para mostrar el formulario de registro
 * y la solicitud POST para procesar los datos del formulario, crear un nuevo usuario
 * y enviar un código de verificación por correo electrónico.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/registro") // Anotación para registrar el servlet en el mapeo de URL "/registro"
public class RegistroController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Servicio para enviar correos electrónicos
    private EmailService emailService = new EmailService();

    /**
     * Método que maneja la solicitud GET. Redirige al usuario a la página de registro.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Redirige a la página de registro
        RequestDispatcher dispatcher = request.getRequestDispatcher("/registro.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Método que maneja la solicitud POST. Procesa los datos del formulario de registro,
     * crea un nuevo usuario, genera un código de verificación, lo envía por correo
     * y redirige al usuario a la página de verificación.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	try {
    	// Crear un nuevo objeto UsuarioDto para almacenar los datos del usuario
        UsuarioDto usuario = new UsuarioDto();

        // Asignar los valores recogidos del formulario usando setters
        usuario.setNombreUsuario(request.getParameter("nombre_usuario"));
        usuario.setApellidosUsuario(request.getParameter("apellidos_usuarios"));
        usuario.setMailUsuario(request.getParameter("mail_usuario"));

        // Convertir la fecha de nacimiento desde el parámetro "fecha_nacimiento_usuario" y asignarla
        String fechaNacimientoStr = request.getParameter("fecha_nacimiento_usuario");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
        usuario.setFechaNacimientoUsuario(Date.valueOf(fechaNacimiento)); // Convertir a java.sql.Date

        // Asignar otros campos del formulario
        usuario.setNicknameUsuario(request.getParameter("nickname_usuario"));

        // Encriptar la contraseña antes de asignarla
        String contrasenya = ContrasenyaEncryptService.encryptPassword(request.getParameter("contrasenya_usuario"));
        usuario.setContrasenyaUsuario(contrasenya);

        // Establecer la fecha de alta (fecha actual)
        usuario.setFechaAltaUsuario(Date.valueOf(LocalDate.now()));

        // Otros campos fijos o recogidos del request
        usuario.setDescripcionUsuario("aaaaa");
        usuario.setDniUsuario(request.getParameter("dni_usuario"));
        usuario.setTelefonoUsuario(request.getParameter("telefono_usuario"));
        usuario.setImgUsuario(null); // No se recibe imagen, se establece en null
        usuario.setRol((long) 1); // Asignar rol predeterminado (1)
        usuario.setGoogleUsuario(false);
        usuario.setTokenRecuperacion(null);
        usuario.setTokenExpiracion(null);

        // Obtener la sesión del usuario
        HttpSession sesion = request.getSession();

        // Generar un código de verificación aleatorio de 6 dígitos
        String codigoVerificacion = String.format("%06d", new Random().nextInt(999999));
        
        // Establecer el código de verificación y el usuario pendiente en la sesión
        sesion.setAttribute("codigo_verificacion", codigoVerificacion);
        sesion.setAttribute("usuarioPendiente", usuario);

        // Enviar el código de verificación por correo electrónico
        emailService.enviarCorreo(usuario.getMailUsuario(), "Código de verificación",
                "Tu código de verificación es: " + codigoVerificacion);

        // Redirigir a la página de verificación
        response.sendRedirect("/webboostly/verificarCodigo");
        
    	} catch (Exception e) {
            e.printStackTrace();
        // si llegas aquí, hubo un fallo
        request.setAttribute("error", "se produjo un error, intentelo mas tarde");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
