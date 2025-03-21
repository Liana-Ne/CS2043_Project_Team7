package FlightManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherService {
    private Random random = new Random();

    public WeatherService() {
        // No API key needed for mock implementation
    }

    public WeatherInfo getWeather(String destination) {
        // Simulate getting weather data
        double temperature = 15 + (random.nextDouble() * 20 - 10); // Temperature between 5 and 25
        String[] conditions = {"Sunny", "Cloudy", "Light Rain", "Clear"};
        String condition = conditions[random.nextInt(conditions.length)];

        WeatherInfo weatherInfo = new WeatherInfo(destination, temperature, condition, new ArrayList<>());
        PackingList packingList = PackingList.generateFromWeather(weatherInfo);
        
        return new WeatherInfo(destination, temperature, condition, packingList.getItems());
    }
} 