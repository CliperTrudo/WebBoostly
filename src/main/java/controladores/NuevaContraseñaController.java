package controladores;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ContrasenyaEncryptService;
import servicios.RecuperacionContrasenya;
import dtos.TokenContraseñaDto;

@WebServlet("/reset-password")
public class NuevaContraseñaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/reset-password.jsp");
        dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String token = request.getParameter("token");
		String nuevaContrasena = ContrasenyaEncryptService.encryptPassword(request.getParameter("nuevaContrasena"));

		TokenContraseñaDto dto = new TokenContraseñaDto();
        dto.setNuevaContrasena(nuevaContrasena);
        dto.setTokenRecuperacion(token);
        boolean resultado = RecuperacionContrasenya.restablecerContrasena(dto);

		if (resultado) {
			request.setAttribute("mensaje", "Contraseña restablecida correctamente.");
		} else {
			request.setAttribute("error", "El token es inválido o ha expirado.");
		}
		request.getRequestDispatcher("reset-password.jsp").forward(request, response);
	}
}
