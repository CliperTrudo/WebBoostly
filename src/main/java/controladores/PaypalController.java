package controladores;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ApiService;

@WebServlet("/Donacion") // Registra el servlet para manejar solicitudes a la ruta "/login"
public class PaypalController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Servicio para interactuar con la API
    private ApiService apiService = new ApiService();

    /**
     * Método encargado de manejar la solicitud GET para mostrar la página de Donacion.
     * Redirige al usuario al formulario de Donacion.
     * @param request La solicitud HTTP recibida.
     * @param response La respuesta HTTP a enviar.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Redirige a la página de login
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pay.jsp");
        dispatcher.forward(request, response);
    }

}
