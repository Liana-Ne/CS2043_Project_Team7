package junittesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class UserTest {
	private User user;
    private static final String TEST_FILE_NAME = "test_itinerary.txt";

    @BeforeEach
    void setUp() {
        user = new User(1, "John Doe", "john.doe@example.com", "New York", "123-456-7890");
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, user.getUserId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("New York", user.getUserLocation());
        assertEquals("123-456-7890", user.getContactInfo());
    }

    @Test
    void testSetters() {
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setUserLocation("Los Angeles");
        user.setContactInfo("987-654-3210");

        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("Los Angeles", user.getUserLocation());
        assertEquals("987-654-3210", user.getContactInfo());
    }

    @Test
    void testSetTripDetails() {
        user.setTripDetails(7);
        assertEquals(7, user.getTripLength());
    }

    @Test
    void testSetSeatPreference() {
        user.setSeatPreference("Window");
        assertEquals("Window", user.getSeatType());
    }

    @Test
    void testSetFlightClassPreference() {
        user.setFlightClassPreference("Business");
        assertEquals("Business", user.getFlightClass());
    }

    @Test
    void testSetInFlightAmenities() {
        user.setInFlightAmenities(true, false, "Premium");
        assertTrue(user.hasWifi());
        assertFalse(user.hasEntertainment());
        assertEquals("Premium", user.getFoodQuality());
    }

    @Test
    void testDisplayUserDetails() {
        user.setTripDetails(7);
        user.setSeatPreference("Window");
        user.setFlightClassPreference("Business");
        user.setInFlightAmenities(true, false, "Premium");
        user.displayUserDetails();
    }

    @Test
    void testGenerateItinerary() {
        user.setTripDetails(7);
        user.setSeatPreference("Window");
        user.setFlightClassPreference("Business");
        user.setInFlightAmenities(true, false, "Premium");
        user.generateItinerary(TEST_FILE_NAME);

        File file = new File(TEST_FILE_NAME);
        assertTrue(file.exists());

        try (FileReader reader = new FileReader(TEST_FILE_NAME)) {
            char[] buffer = new char[1024];
            int length = reader.read(buffer);
            String content = new String(buffer, 0, length);
            assertTrue(content.contains("User ID       : 1"));
            assertTrue(content.contains("Name          : John Doe"));
            assertTrue(content.contains("Flight Class  : Business"));
        } catch (IOException e) {
            fail("Failed to read the itinerary file");
        }
    }

    @Test
    void testShowItineraryForUser() {
        user.generateItinerary(TEST_FILE_NAME);
        User.showItineraryForUser(TEST_FILE_NAME, "John Doe");
        User.showItineraryForUser(TEST_FILE_NAME, "Non Existent User");
    }


}
