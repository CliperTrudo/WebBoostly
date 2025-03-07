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

/**
 * Controlador encargado de gestionar la recuperación de contraseñas.
 * Este controlador maneja la solicitud GET para mostrar la página de recuperación de contraseña,
 * y la solicitud POST para generar un token de recuperación y enviarlo por correo electrónico.
 * 
 * @author Sergio Alfonseca
 */
@WebServlet("/recuperar-contrasena") // Anotación para registrar el servlet en el mapeo de URL "/recuperar-contrasena"
public class RecuperacionController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método que maneja la solicitud GET. Redirige a la página de recuperación de contraseña.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Redirigir a la página de recuperación
        RequestDispatcher dispatcher = request.getRequestDispatcher("/recuperar.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Método que maneja la solicitud POST. Recibe el correo electrónico, genera un token de recuperación,
     * y lo envía a través del servicio de recuperación de contraseña.
     * 
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el correo electrónico del formulario
        String email = request.getParameter("email");
        
        // Generar un token único para la recuperación
        String token = UUID.randomUUID().toString();

        // Crear un objeto TokenContraseñaDto con los datos obtenidos
        TokenContraseñaDto dto = new TokenContraseñaDto();
        dto.setEmail(email);
        dto.setTokenRecuperacion(token);

        System.out.println(dto.toString());

        // Llamar al servicio para enviar el token de recuperación
        boolean resultado = RecuperacionContrasenya.enviarTokenRecuperacion(dto);

        // Establecer el mensaje adecuado según el resultado
        if (resultado) {
            request.setAttribute("mensaje", "Se ha enviado un correo con instrucciones para recuperar la contraseña.");
        } else {
            request.setAttribute("error", "No se pudo generar la solicitud de recuperación.");
        }

        // Redirigir a la página de recuperación con el mensaje correspondiente
        request.getRequestDispatcher("recuperar.jsp").forward(request, response);
    }
}
