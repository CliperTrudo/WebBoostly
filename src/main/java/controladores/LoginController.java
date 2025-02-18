package controladores;

import java.io.IOException;
import java.time.LocalDate;

import dtos.MailContrasenyaRequestDto;
import dtos.UsuarioDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.ApiService;
import servicios.ContrasenyaEncryptService;




@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApiService apiService = new ApiService();
	private ContrasenyaEncryptService encoder = new ContrasenyaEncryptService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

        // Contraseña en texto plano
        String plainPassword = "miContraseña";

        // Encriptar la contraseña
        String hashedPassword = encoder.encryptPassword(plainPassword);
        
        UsuarioDto usuario = new UsuarioDto(
        	    null, // id (se genera automáticamente, por eso null)
        	    "Sergio", // nombreUsuario
        	    "Alfonseca", // apellidosUsuario
        	    "alfonseca.sergio@gmail.com", // mailUsuario
        	    LocalDate.parse("2004-04-28"), // fechaNacimientoUsuario (formato ISO: yyyy-MM-dd)
        	    "cliper", // nicknameUsuario
        	    "$2a$10$.Z7l3axdULFJbyEnlEdjZOoo0auLuW1Bs9xh90QeMulsIsqbCaB7y", // contrasenyaUsuario
        	    LocalDate.parse("2025-02-17"), // fechaAltaUsuario
        	    "aaaaa", // descripcionUsuario
        	    "52076824V", // dniUsuario
        	    "644605764", // telefonoUsuario
        	    null, // imgUsuario
        	    "Usuario", // rolUsuario
        	    false, // googleUsuario
        	    null, // tokenRecuperacion
        	    null  // tokenExpiracion
        	);
        
        usuario.setContrasenyaUsuario(hashedPassword);
        
        UsuarioDto usuarioBD = new UsuarioDto();
        
        usuario.setContrasenyaUsuario(hashedPassword);
        
        usuarioBD = apiService.encript(usuario, null);

        // Mostrar resultados
        System.out.println("Contraseña introducida: " + usuario.getContrasenyaUsuario());
        System.out.println("Contraseña introducida hash: " + usuario.getContrasenyaUsuario());
        System.out.println("Contraseña BD: " + usuarioBD.getContrasenyaUsuario());
        System.out.println("Contraseña coincide?: " + usuario.getContrasenyaUsuario().equals(usuarioBD.getContrasenyaUsuario()));

        // Verificar si la contraseña en texto plano coincide con el hash
        boolean matches = encoder.matches(plainPassword, usuarioBD.getContrasenyaUsuario());
        System.out.println("¿La contraseña coincide? " + matches);


		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
	}
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("email");
		String password =request.getParameter("password");
		
		HttpSession sesion = request.getSession();

		MailContrasenyaRequestDto mailpass = new MailContrasenyaRequestDto(username, password);
		System.out.println(mailpass.toString());
		
		String respuesta = apiService.sendLoginData(mailpass, sesion);
		
		System.out.println(respuesta);
		if ("success".equalsIgnoreCase(respuesta)) {

			response.sendRedirect("inicio");

			return; // Redirige a ventanaPrincipal

		} else {
			sesion.setAttribute("error", "Credenciales incorrectas"); // Error al login
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
	        dispatcher.forward(request, response);
		}

		return;
	}

}
