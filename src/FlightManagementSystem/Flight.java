package FlightManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private static final String[] FIRST_CLASS_LETTERS = {"A", "B", "C", "D"};
    private static final String[] REGULAR_LETTERS = {"A", "B", "C", "D", "E", "F"};
    private static final String[] SEAT_CLASSES = {"First Class", "Business", "Economy"};
    private static final Map<String, Double> CLASS_PRICES = new HashMap<>();
    
    static {
        CLASS_PRICES.put("Economy", 1.0);
        CLASS_PRICES.put("Business", 2.5);
        CLASS_PRICES.put("First Class", 4.0);
    }

    public Flight(String flightNumber, String origin, String destination) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isSeatAvailable(String key) {
        return true;  // All seats are always available
    }

    public boolean bookSeat(String seatNumber, String seatClass) {
        return true;  // Always succeeds
    }

    public static String[] getSeatClasses() {
        return SEAT_CLASSES;
    }

    public static String[] getSeatLetters(String seatClass) {
        if ("First Class".equals(seatClass)) {
            return FIRST_CLASS_LETTERS;
        }
        return REGULAR_LETTERS;
    }

    public double getPrice(String seatClass) {
        double basePrice = 500.0; // Base price for economy
        return basePrice * CLASS_PRICES.getOrDefault(seatClass, 1.0);
    }

    @Override
    public String toString() {
        return destination;
    }
}
