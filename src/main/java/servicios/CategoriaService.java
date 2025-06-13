package servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dtos.CategoriaDto;

public class CategoriaService {
    private static final String API_BASE = "http://localhost:8081/apiBoostly/api"; 
    private final ObjectMapper mapper;

    public CategoriaService() {
        this.mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    }

    public List<CategoriaDto> listarCategorias() {
        try {
            URL url = new URL(API_BASE + "/categorias");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            if (code == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                CategoriaDto[] arr = mapper.readValue(in, CategoriaDto[].class);
                in.close();
                return Arrays.asList(arr);
            } else {
                System.err.println("Error al obtener categorías: " + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
