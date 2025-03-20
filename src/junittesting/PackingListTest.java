package junittesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;


class PackingListTest {

	private PackingList packingList;
    private Flight flight;
    private List<String> weatherConditions;

    @BeforeEach
    void setUp() {
        flight = new Flight("F101", "Fredericton", "London", 5);
        weatherConditions = new ArrayList<>();
        weatherConditions.add("Cold");
        weatherConditions.add("Rainy");
        packingList = new PackingList(flight, weatherConditions, 10);
    }

    @AfterEach
    void tearDown() {
        packingList = null;
        flight = null;
        weatherConditions = null;
    }

    @Test
    void testGeneratePackingList() {
        packingList.generatePackingList();
        List<String> recommendedItems = packingList.getRecommendedItems();

        assertTrue(recommendedItems.contains("Passport"));
        assertTrue(recommendedItems.contains("Warm Jacket"));
        assertTrue(recommendedItems.contains("Umbrella"));
        assertTrue(recommendedItems.contains("Extra Clothes"));
        assertTrue(recommendedItems.contains("Camera"));
    }

    @Test
    void testDisplayPackingList() {
        packingList.generatePackingList();
        packingList.displayPackingList();
    }

    @Test
    void testGetRecommendedItems() {
        packingList.generatePackingList();
        List<String> recommendedItems = packingList.getRecommendedItems();

        assertNotNull(recommendedItems);
        assertFalse(recommendedItems.isEmpty());
        assertTrue(recommendedItems.contains("Travel Documents"));
        assertTrue(recommendedItems.contains("Sunscreen") == false);
    }

    @Test
    void testHotWeatherPacking() {
        weatherConditions.clear();
        weatherConditions.add("Hot");
        packingList = new PackingList(flight, weatherConditions, 5);
        packingList.generatePackingList();
        List<String> recommendedItems = packingList.getRecommendedItems();

        assertTrue(recommendedItems.contains("Sunscreen"));
        assertTrue(recommendedItems.contains("Swimsuit"));
        assertTrue(recommendedItems.contains("Light Clothing"));
    }

    @Test
    void testLongVacationPacking() {
        packingList = new PackingList(flight, weatherConditions, 15);
        packingList.generatePackingList();
        List<String> recommendedItems = packingList.getRecommendedItems();

        assertTrue(recommendedItems.contains("Extra Clothes"));
        assertTrue(recommendedItems.contains("Laundry Supplies"));
    }
	

}
