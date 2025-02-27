package com.example.weatherapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WeatherController {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(@RequestParam String destination) {

        String url = BASE_URL + "?q=" + destination + "&appid=" + apiKey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());

            JSONObject main = jsonResponse.getJSONObject("main");
            double temperature = main.getDouble("temp");

            String condition = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

            JSONObject result = new JSONObject();
            result.put("destination", destination);
            result.put("temperature", temperature + "°C");
            result.put("condition", condition);
            result.put("packing_recommendations", getPackingRecommendations(temperature, condition));

            return ResponseEntity.ok(result.toString());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch weather data.");
        }
    }

    private List<String> getPackingRecommendations(double temperature, String condition) {
        List<String> recommendations = new ArrayList<>();

        if (temperature < 10) {
            recommendations.add("Jacket");
            recommendations.add("Gloves");
            recommendations.add("Thermal wear");
            recommendations.add("Boots");
        } else if (temperature > 25) {
            recommendations.add("Light clothing");
            recommendations.add("Sunglasses");
            recommendations.add("Sunscreen");
        } else {
            recommendations.add("Casual wear");
            recommendations.add("Sneakers");
        }

        if (condition.toLowerCase().contains("rain")) {
            recommendations.add("Umbrella");
            recommendations.add("Raincoat");
            recommendations.add("Waterproof shoes");
        }

        return recommendations;
    }
}
