package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.ProyectoDto;

/**
 * Clase encargada de obtener proyectos por categoría desde la API.
 * Esta clase se comunica con la API para obtener una lista de proyectos que pertenecen a una categoría específica.
 */
public class ProyectosCategoriaService {

    // URL base para la API que devuelve los proyectos por categoría
    private static final String API_BASE_URL = "http://localhost:8081/apiBoostly/api/proyectos/categoria/";

    /**
     * Método que obtiene una lista de proyectos por ID de categoría.
     * Realiza una solicitud GET a la API para obtener los proyectos asociados con la categoría indicada.
     *
     * @param idCategoria El ID de la categoría para la cual se desean obtener los proyectos.
     * @return Una lista de objetos ProyectoDto con los proyectos correspondientes a la categoría.
     */
    public List<ProyectoDto> obtenerProyectosPorCategoria(Long idCategoria) {
        // Construir la URL de la solicitud GET usando el ID de categoría
        String url = API_BASE_URL + idCategoria;

        try {
            // Crear una URI a partir de la URL
            URI uri = new URI(url);
            URL apiUrl = uri.toURL();

            // Establecer una conexión HTTP con la API
            HttpURLConnection conexion = (HttpURLConnection) apiUrl.openConnection();
            conexion.setRequestMethod("GET");  // Establecer el método HTTP como GET
            conexion.setRequestProperty("Accept", "application/json");  // Indicar que se espera una respuesta en formato JSON

            // Obtener el código de respuesta de la API
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {  // Si la respuesta es exitosa
                // Leer la respuesta de la API
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {  // Leer cada línea de la respuesta
                    response.append(inputLine);
                }
                in.close();  // Cerrar el BufferedReader

                // Convertir la respuesta JSON en una lista de objetos ProyectoDto usando Jackson
                ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());  // Crear un ObjectMapper con soporte para fechas
                return Arrays.asList(mapper.readValue(response.toString(), ProyectoDto[].class));  // Convertir el JSON a un arreglo de ProyectoDto y luego a lista
            } else {
                // Si la respuesta no es exitosa, imprimir el código de respuesta de la API
                System.out.println("Error al obtener proyectos por categoría: " + codigoRespuesta);
            }
        } catch (Exception e) {
            // Imprimir el error si ocurre una excepción durante la conexión o procesamiento
            e.printStackTrace();
        }
        // Retornar una lista vacía si ocurre algún error
        return Collections.emptyList();
    }
}
