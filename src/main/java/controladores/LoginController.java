package controladores;

import java.io.IOException;

import dtos.MailContrasenyaRequestDto;
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
