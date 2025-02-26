package controladores;

import java.io.IOException;
import java.time.LocalDate;

import dtos.MailContrasenyaRequestDto;
import dtos.SesionDto;
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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
	}
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("email");
		String password = ContrasenyaEncryptService.encryptPassword(request.getParameter("password"));
		
		HttpSession sesion = request.getSession();

		MailContrasenyaRequestDto mailpass = new MailContrasenyaRequestDto(username, password);
		System.out.println(mailpass.toString());
		
		UsuarioDto usuarioEncoradoDto = apiService.sendLoginData(mailpass, sesion);
	
		
		System.out.println("Contraseña ingresada: " + request.getParameter("password"));
		System.out.println("Contraseña a comparar: " + (usuarioEncoradoDto.getContrasenyaUsuario()));

		// Verificar si la contraseña en texto plano coincide con el hash
		boolean matches = ContrasenyaEncryptService.matches(request.getParameter("password"),
				usuarioEncoradoDto.getContrasenyaUsuario());
		System.out.println("¿La contraseña coincide? " + matches);

		String respuesta;
		if (matches) {
			System.out.println("Contraseña correcta");
			SesionDto sesionUsu = new SesionDto();
			sesionUsu.setId(usuarioEncoradoDto.getId());
			sesionUsu.setMailUsuario(usuarioEncoradoDto.getMailUsuario());
			sesionUsu.setRolUsuario(usuarioEncoradoDto.getRol());
			sesion.setAttribute("datos", sesionUsu);
			sesion.setAttribute("nombreUsuario", usuarioEncoradoDto.getNicknameUsuario());
			respuesta = "success";
		} else {
			// La contraseña es incorrecta
			respuesta = "error";
		}
		
		System.out.println(respuesta);
		if ("success".equalsIgnoreCase(respuesta)) {
			
			response.sendRedirect("/webboostly");

			return; // Redirige a ventanaPrincipal

		} else {
			sesion.setAttribute("error", "Credenciales incorrectas"); // Error al login
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
	        dispatcher.forward(request, response);
		}

		return;
	}

}
