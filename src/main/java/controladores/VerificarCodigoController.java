package controladores;

import java.io.IOException;

import dtos.UsuarioDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.ApiService;

@WebServlet("/verificarCodigo")
public class VerificarCodigoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ApiService apiService = new ApiService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
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
            String respuesta = apiService.registroUsuario(usuarioPendiente, sesion);
            if ("success".equalsIgnoreCase(respuesta)) {
                // El registro fue exitoso, redirigir a la página de inicio
                response.sendRedirect("inicio");
            } else {
                // Si el registro falla, mostrar el error
                request.setAttribute("error", "Hubo un error al registrar el usuario. Por favor, inténtalo de nuevo.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            }

            // Eliminar el código de verificación de la sesión
            sesion.removeAttribute("codigo_verificacion");
        } else {
            // Si el código es incorrecto, mostrar un mensaje de error
            request.setAttribute("error", "El código de verificación es incorrecto. Por favor, inténtalo de nuevo.");
            request.getRequestDispatcher("/verificar.jsp").forward(request, response);
        }
    }
}
