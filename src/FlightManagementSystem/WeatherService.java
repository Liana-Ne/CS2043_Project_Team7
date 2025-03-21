package FlightManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherService {
    private final Random random = new Random();

    public WeatherService(String apiKey) {
        // API key not used in the mock implementation
    }

    public WeatherInfo getWeather(String destination) {
        // For demonstration, return simulated weather data
        double temperature = 15 + random.nextDouble() * 20; // Random temp between 15-35
        String[] conditions = {"Sunny", "Cloudy", "Partly Cloudy", "Light Rain", "Clear"};
        String condition = conditions[random.nextInt(conditions.length)];
        
        WeatherInfo weatherInfo = new WeatherInfo(destination, temperature, condition, new ArrayList<>());
        PackingList packingList = PackingList.generateFromWeather(weatherInfo);
        
        return new WeatherInfo(destination, temperature, condition, packingList.getItems());
    }
} 