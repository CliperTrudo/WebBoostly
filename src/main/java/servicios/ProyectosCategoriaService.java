package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.ProyectoDto;

public class ProyectosCategoriaService {

    private static final String API_BASE_URL = "http://localhost:8081/apiBoostly/api/proyectos/categoria/";

    public List<ProyectoDto> obtenerProyectosPorCategoria(Long idCategoria) {
        String url = API_BASE_URL + idCategoria;

        try {
            URI uri = new URI(url);
            URL apiUrl = uri.toURL();

            HttpURLConnection conexion = (HttpURLConnection) apiUrl.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("Accept", "application/json");

            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
                return Arrays.asList(mapper.readValue(response.toString(), ProyectoDto[].class));
            } else {
                System.out.println("Error al obtener proyectos por categoría: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
