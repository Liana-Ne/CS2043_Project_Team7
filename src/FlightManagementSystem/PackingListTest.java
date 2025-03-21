package FlightManagementSystem;

/**
 * Test class for PackingList functionality.
 * 
 * 
 * 
 * Test cases cover:
 * - Adding and removing items
 * - Handling null/empty items
 * - Weather-based packing list generation for different conditions:
 *   * Cold weather (< 10°C)
 *   * Warm weather (> 25°C)
 *   * Rainy conditions
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class PackingListTest {
    private PackingList packingList;
    
    @Before
    public void setUp() {
        packingList = new PackingList();
    }
    
    @Test
    public void testAddItem() {
        packingList.addItem("Passport");
        List<String> items = packingList.getItems();
        assertEquals(1, items.size());
        assertEquals("Passport", items.get(0));
    }
    
    @Test
    public void testAddNullItem() {
        packingList.addItem(null);
        assertTrue(packingList.getItems().isEmpty());
    }
    
    @Test
    public void testAddEmptyItem() {
        packingList.addItem("");
        packingList.addItem("   ");
        assertTrue(packingList.getItems().isEmpty());
    }
    
    @Test
    public void testRemoveItem() {
        packingList.addItem("Passport");
        packingList.addItem("Wallet");
        packingList.removeItem("Passport");
        List<String> items = packingList.getItems();
        assertEquals(1, items.size());
        assertEquals("Wallet", items.get(0));
    }
    
    @Test
    public void testGenerateFromWeatherCold() {
        WeatherInfo weatherInfo = new WeatherInfo("Toronto", 5.0, "Clear", null);
        PackingList generated = PackingList.generateFromWeather(weatherInfo);
        List<String> items = generated.getItems();
        assertTrue(items.contains("Winter Coat"));
        assertTrue(items.contains("Gloves"));
        assertFalse(items.contains("Sunscreen"));
    }
    
    @Test
    public void testGenerateFromWeatherWarm() {
        WeatherInfo weatherInfo = new WeatherInfo("Toronto", 25.0, "Sunny", null);
        PackingList generated = PackingList.generateFromWeather(weatherInfo);
        List<String> items = generated.getItems();
        assertTrue(items.contains("Sunscreen"));
        assertTrue(items.contains("Sunglasses"));
        assertFalse(items.contains("Winter Coat"));
    }
    
    @Test
    public void testGenerateFromWeatherRainy() {
        WeatherInfo weatherInfo = new WeatherInfo("Toronto", 15.0, "Light Rain", null);
        PackingList generated = PackingList.generateFromWeather(weatherInfo);
        List<String> items = generated.getItems();
        assertTrue(items.contains("Umbrella"));
        assertTrue(items.contains("Rain Jacket"));
        assertTrue(items.contains("Waterproof Shoes"));
    }
} 