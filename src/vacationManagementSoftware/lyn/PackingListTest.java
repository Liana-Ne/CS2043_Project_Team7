package vacationManagementSoftware.lyn;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;



class PackingListTest {
   private PackingList packingList;

    @BeforeEach
    void setUp() {
    	
       packingList = new PackingList("Paris", 3, "Summer");
    }
    
    @Test
    void testBasicPackingList() {
        List<String> items = packingList.generatePackingList();
        assertTrue(items.contains("Clothes"));
        assertTrue(items.contains("Toiletries"));
        assertTrue(items.contains("Passport"));
    }
    
    @Test
    void testExtraShoesForLongTrip() {
       PackingList longTrip = new PackingList("Paris", 7, "Summer");
       List<String> items = longTrip.generatePackingList();
       assertTrue(items.contains("Extra Shoes"));
    }
    
    @Test
    void testParisWinterPackingList() {
      PackingList parisWinter = new PackingList("Paris", 3, "Winter");
      List<String> items = parisWinter.generatePackingList();
      assertTrue(items.contains("Umbrella"));
      assertTrue(items.contains("Scarf"));
      assertTrue(items.contains("Gloves"));
    }
    
    @Test
    void testLondonWinterPackingList() {
      PackingList londonWinter = new PackingList("London", 3, "Winter");
      List<String> items = londonWinter.generatePackingList();
      assertTrue(items.contains("Raincoat"));
      assertTrue(items.contains("Umbrella"));
      assertTrue(items.contains("Warm Coat"));
    }
    
    @Test
    void testEmptyDestination() {
      PackingList emptyDestination = new PackingList("", 3, "Summer");
      List<String> items = emptyDestination.generatePackingList();
      assertTrue(items.contains("Check local weather for season-specific items"));
    }
    
    @Test
    void testParisSummerPackingList() {
       PackingList parisSummer = new PackingList("Paris", 3, "Summer");
       List<String> items = parisSummer.generatePackingList();
       assertTrue(items.contains("Sunglasses"));
       assertTrue(items.contains("Hat"));
    }

}
