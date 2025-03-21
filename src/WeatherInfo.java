import java.util.List;

public class WeatherInfo {
    private final String destination;
    private final double temperature;
    private final String condition;
    private final List<String> packingRecommendations;

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
        return String.format(
            "Weather in %s:%nTemperature: %.1f°C%nCondition: %s%nPacking Recommendations:%n%s",
            destination,
            temperature,
            condition,
            String.join("%n", packingRecommendations)
        );
    }
} 