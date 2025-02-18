package controladores;

import java.io.IOException;
import java.util.UUID;

import dtos.TokenContraseñaDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.RecuperacionContrasenya;

@WebServlet("/recuperar-contrasena")
public class RecuperacionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/recuperar.jsp");
        dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString(); // Genera un token único

        TokenContraseñaDto dto = new TokenContraseñaDto();
        dto.setEmail(email);
        dto.setTokenRecuperacion(token);
        
        System.out.println(dto.toString());
        boolean resultado = RecuperacionContrasenya.enviarTokenRecuperacion(dto);

        if (resultado) {
            request.setAttribute("mensaje", "Se ha enviado un correo con instrucciones para recuperar la contraseña.");
        } else {
            request.setAttribute("error", "No se pudo generar la solicitud de recuperación.");
        }
        request.getRequestDispatcher("recuperar.jsp").forward(request, response);
    }
}