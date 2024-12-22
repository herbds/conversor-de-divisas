package com.example.demo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CambioDivisasService {

    // URL API
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/4d0c39ba9b7be7848e99f4c7/latest/";

    // Tasa de cambio divisas
    public double obtenerTasaDeCambio(String divisaOrigen, String divisaDestino) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = API_URL + divisaOrigen;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Solicitud HTTP GET
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        // JSON como objeto; búsqueda tasas de cambio
        JsonObject jsonResponse = JsonParser.parseString(body).getAsJsonObject();
        JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");

        return conversionRates.get(divisaDestino).getAsDouble();
    }
}
