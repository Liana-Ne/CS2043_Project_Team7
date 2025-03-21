import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WeatherConfig {
    private static final String CONFIG_FILE = "weather.properties";
    private static String apiKey;

    static {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            props.load(fis);
            fis.close();
            
            apiKey = props.getProperty("openweather.api.key");
            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new RuntimeException("OpenWeather API key not found in weather.properties");
            }
            apiKey = apiKey.trim();
            System.out.println("Weather API key loaded successfully");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load weather.properties file: " + e.getMessage());
        }
    }

    public static String getApiKey() {
        return apiKey;
    }
} 