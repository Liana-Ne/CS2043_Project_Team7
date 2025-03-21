package FlightManagementSystem;

import java.util.List;
import java.util.ArrayList;

public class WeatherInfo {
    private String destination;
    private double temperature;
    private String condition;
    private List<String> packingList;

    public WeatherInfo(String destination, double temperature, String condition, List<String> packingList) {
        this.destination = destination;
        this.temperature = temperature;
        this.condition = condition;
        this.packingList = packingList != null ? packingList : new ArrayList<>();
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

    public List<String> getPackingList() {
        return new ArrayList<>(packingList);
    }

    @Override
    public String toString() {
        return String.format("Weather in %s: %.1f°C, %s", 
            destination, temperature, condition);
    }
} 