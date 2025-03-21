package FlightManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private Map<String, Boolean> seats; // Format: "1A-ECO", "1B-ECO", etc.
    private static final String[] SEAT_LETTERS = {"A", "B", "C", "D", "E", "F"};
    private static final String[] SEAT_CLASSES = {"Economy", "Business", "First Class"};
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
        this.seats = new HashMap<>();
        initializeSeats();
    }

    private void initializeSeats() {
        // First Class (Rows 1-5)
        for (int row = 1; row <= 5; row++) {
            for (String letter : SEAT_LETTERS) {
                seats.put(row + letter + "-First Class", false);
            }
        }
        // Business (Rows 6-15)
        for (int row = 6; row <= 15; row++) {
            for (String letter : SEAT_LETTERS) {
                seats.put(row + letter + "-Business", false);
            }
        }
        // Economy (Rows 16-50)
        for (int row = 16; row <= 50; row++) {
            for (String letter : SEAT_LETTERS) {
                seats.put(row + letter + "-Economy", false);
            }
        }
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
        return seats.containsKey(key) && !seats.get(key);
    }

    public boolean bookSeat(String seatNumber, String seatClass) {
        String key = seatNumber + "-" + seatClass;
        if (isSeatAvailable(key)) {
            seats.put(key, true);
            return true;
        }
        return false;
    }

    public int getBookedSeats() {
        int count = 0;
        for (Boolean isBooked : seats.values()) {
            if (isBooked) count++;
        }
        return count;
    }

    public int getCapacity() {
        return seats.size();
    }

    public static String[] getSeatClasses() {
        return SEAT_CLASSES;
    }

    public static String[] getSeatLetters() {
        return SEAT_LETTERS;
    }

    public double getPrice(String seatClass) {
        double basePrice = 500.0; // Base price for economy
        return basePrice * CLASS_PRICES.getOrDefault(seatClass, 1.0);
    }

    @Override
    public String toString() {
        return String.format("Flight %s to %s", flightNumber, destination);
    }
}
