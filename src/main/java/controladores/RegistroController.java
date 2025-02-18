package controladores;

import java.io.IOException;
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

@WebServlet("/registro")
public class RegistroController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EmailService emailService = new EmailService();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/registro.jsp");
        dispatcher.forward(request, response);
	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Crear el objeto UsuarioDto mediante el constructor vacío
        UsuarioDto usuario = new UsuarioDto();
        
        // Asignar los valores recogidos del request usando setters
        usuario.setNombreUsuario(request.getParameter("nombre_usuario"));
        usuario.setApellidosUsuario(request.getParameter("apellidos_usuarios"));
        usuario.setMailUsuario(request.getParameter("mail_usuario"));
        
        // Convertir la fecha de nacimiento (se asume formato "yyyy-MM-dd")
        String fechaNacimientoStr = request.getParameter("fecha_nacimiento_usuario");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
        usuario.setFechaNacimientoUsuario(fechaNacimiento);
        
        usuario.setNicknameUsuario(request.getParameter("nickname_usuario"));
        
        // Encriptar la contraseña antes de asignarla
        String contrasenya = ContrasenyaEncryptService.encryptPassword(request.getParameter("contrasenya_usuario"));
        usuario.setContrasenyaUsuario(contrasenya);
        
        // Fecha de alta: se usa la fecha actual
        usuario.setFechaAltaUsuario(LocalDate.now());
        
        // Otros campos fijos o recogidos del request
        usuario.setDescripcionUsuario("aaaaa");
        usuario.setDniUsuario(request.getParameter("dni_usuario"));
        usuario.setTelefonoUsuario(request.getParameter("telefono_usuario"));
        usuario.setImgUsuario(null);  // No se recibe imagen, se establece en null
        usuario.setRolUsuario("Usuario");
        usuario.setGoogleUsuario(false);
        usuario.setTokenRecuperacion(null);
        usuario.setTokenExpiracion(null);

        // Obtener la sesión
        HttpSession sesion = request.getSession();
        
        // Generar un código de verificación (6 dígitos)
        String codigoVerificacion = String.format("%06d", new Random().nextInt(999999));
        sesion.setAttribute("codigo_verificacion", codigoVerificacion);
        sesion.setAttribute("usuarioPendiente", usuario);
        
        // Enviar el código de verificación por correo
        emailService.enviarCorreo(
            usuario.getMailUsuario(),
            "Código de verificación",
            "Tu código de verificación es: " + codigoVerificacion
        );
        
        // Redirigir a la página de verificación
        response.sendRedirect("verificar.jsp");
    }

}