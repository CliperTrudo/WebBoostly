package controladores;

import java.io.IOException;

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

/**
 * Controlador encargado de manejar la verificación del código de registro. Este
 * controlador procesa la verificación del código ingresado por el usuario y
 * realiza el registro del nuevo usuario si el código es correcto.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/verificarCodigo") // Anotación para registrar el servlet en el mapeo de URL "/verificarCodigo"
public class VerificarCodigoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Servicio para interactuar con la API y registrar el usuario
	private ApiService apiService = new ApiService();

	/**
	 * Método que maneja la solicitud GET para mostrar la página de verificación del
	 * código.
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

		// Redirige a la página de verificación
		RequestDispatcher dispatcher = request.getRequestDispatcher("/verificar.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Método que maneja la solicitud POST para procesar el código ingresado por el
	 * usuario. Verifica si el código es correcto y, si es así, registra al usuario.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Recoger el código ingresado por el usuario
			String codigoIngresado = request.getParameter("codigo_verificacion");

			// Obtener el código generado almacenado en la sesión
			HttpSession sesion = request.getSession();
			String codigoGenerado = (String) sesion.getAttribute("codigo_verificacion");

			// Comprobar si el código ingresado es correcto
			if (codigoIngresado != null && codigoIngresado.equals(codigoGenerado)) {
				// El código es correcto, proceder con el registro del usuario
				UsuarioDto usuarioPendiente = (UsuarioDto) sesion.getAttribute("usuarioPendiente");

				// Registrar al usuario en la base de datos
				String respuesta = apiService.registroUsuario(usuarioPendiente);
				if ("success".equalsIgnoreCase(respuesta)) {
					System.out.println("Registro Exitoso");
					// El registro fue exitoso, redirigir a la página principal
					SesionDto sesionUsu = new SesionDto();
					sesionUsu.setId(usuarioPendiente.getId());
					sesionUsu.setMailUsuario(usuarioPendiente.getMailUsuario());
					sesionUsu.setRolUsuario(usuarioPendiente.getRol());
					sesion.setAttribute("datos", sesionUsu);

					response.sendRedirect("/webboostly");
				} else {
					// Si el registro falla, mostrar el error
					request.setAttribute("error",
							"Hubo un error al registrar el usuario. Por favor, inténtalo de nuevo.");
					request.getRequestDispatcher("/registro.jsp").forward(request, response);
				}

				// Eliminar el código de verificación de la sesión
				sesion.removeAttribute("codigo_verificacion");
			} else {
				// Si el código es incorrecto, mostrar un mensaje de error
				request.setAttribute("error",
						"El código de verificación es incorrecto. Por favor, inténtalo de nuevo.");
				request.getRequestDispatcher("/verificar.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// si llegas aquí, hubo un fallo
			request.setAttribute("error", "se produjo un error, intentelo mas tarde");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}
}
