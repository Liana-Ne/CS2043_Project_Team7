package FlightManagementSystem;

import java.util.List;

public class WeatherInfo {
    private String destination;
    private double temperature;
    private String condition;
    private List<String> packingRecommendations;

    public WeatherInfo(String destination, double temperature, String condition, List<String> packingRecommendations) {
        this.destination = destination;
        this.temperature = temperature;
        this.condition = condition;
        this.packingRecommendations = packingRecommendations;
    }

    public String getDestination() {
        return destination;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }

    public List<String> getPackingRecommendations() {
        return packingRecommendations;
    }

    @Override
    public String toString() {
        return String.format("%s: %.1f°C, %s", destination, temperature, condition);
    }
} 