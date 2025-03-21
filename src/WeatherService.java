import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    private final String apiKey;
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherService(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API key cannot be null or empty");
        }
        this.apiKey = apiKey.trim();
    }

    public WeatherInfo getWeather(String destination) throws Exception {
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be null or empty");
        }

        String encodedDestination = URLEncoder.encode(destination.trim(), StandardCharsets.UTF_8.toString());
        String urlString = BASE_URL + "?q=" + encodedDestination + "&appid=" + apiKey + "&units=metric";
        
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject main = jsonResponse.getJSONObject("main");
            double temperature = main.getDouble("temp");
            String condition = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

            return new WeatherInfo(
                destination,
                temperature,
                condition,
                getPackingRecommendations(temperature, condition)
            );
        } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new RuntimeException("City not found. Please try using the English name of the city (e.g., 'Tokyo' instead of '東京')");
        } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            throw new RuntimeException("Invalid API key. Please check your OpenWeather API key configuration.");
        } else {
            throw new RuntimeException("Failed to fetch weather data. Response code: " + responseCode);
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