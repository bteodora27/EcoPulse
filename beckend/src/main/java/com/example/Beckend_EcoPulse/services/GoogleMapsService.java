package com.example.Beckend_EcoPulse.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public double[] getCoordinates(String address) {
        try {
            String endpoint = "https://maps.googleapis.com/maps/api/geocode/json?address="
                    + address.replace(" ", "+") + "&key=" + apiKey;
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(responseBuilder.toString());

            if ("OK".equals(responseJson.get("status").asText())) {
                JsonNode location = responseJson.get("results").get(0).get("geometry").get("location");
                double lat = location.get("lat").asDouble();
                double lng = location.get("lng").asDouble();
                return new double[]{lat, lng};
            } else {
                throw new RuntimeException("Google Maps API error: " + responseJson.get("status").asText());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get location coordinates: " + e.getMessage(), e);
        }
    }
}
