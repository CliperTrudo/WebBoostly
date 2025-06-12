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

/**
 * Controlador encargado de manejar la solicitud de restablecimiento de
 * contraseña. Este controlador procesa tanto las solicitudes GET como POST para
 * la recuperación de contraseñas. En la solicitud GET, redirige al formulario
 * donde el usuario puede introducir su nueva contraseña. En la solicitud POST,
 * procesa el token de recuperación y la nueva contraseña para restablecer la
 * contraseña del usuario.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/reset-password")
public class NuevaContraseñaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Método encargado de manejar la solicitud GET. Redirige al usuario al
	 * formulario de restablecimiento de contraseña.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Redirige a la página de restablecimiento de contraseña
		RequestDispatcher dispatcher = request.getRequestDispatcher("/reset-password.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Método encargado de manejar la solicitud POST para restablecer la contraseña
	 * del usuario. Verifica el token de recuperación y actualiza la contraseña si
	 * el token es válido.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Obtener el token de recuperación y la nueva contraseña del formulario
			String token = request.getParameter("token");
			String nuevaContrasena = ContrasenyaEncryptService.encryptPassword(request.getParameter("nuevaContrasena"));

			// Crear un DTO con los datos necesarios para restablecer la contraseña
			TokenContraseñaDto dto = new TokenContraseñaDto();
			dto.setNuevaContrasena(nuevaContrasena);
			dto.setTokenRecuperacion(token);

			// Llamar al servicio de recuperación de contraseña
			boolean resultado = RecuperacionContrasenya.restablecerContrasena(dto);

			// Si el resultado es exitoso, mostrar mensaje de éxito, de lo contrario,
			// mensaje de error
			if (resultado) {
				request.setAttribute("mensaje", "Contraseña restablecida correctamente.");
			} else {
				request.setAttribute("error", "El token es inválido o ha expirado.");
			}

			// Redirigir al usuario de nuevo al formulario con el mensaje correspondiente
			request.getRequestDispatcher("reset-password.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// si llegas aquí, hubo un fallo
			request.setAttribute("error", "se produjo un error, intentelo mas tarde");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}
}
