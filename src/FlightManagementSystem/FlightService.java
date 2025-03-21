package FlightManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class FlightService {
    private List<Flight> flights;
    private static final String ORIGIN = "Fredericton";

    public FlightService() {
        flights = new ArrayList<>();
        initializeFlights();
    }

    private void initializeFlights() {
        // North American Destinations
        addFlight("NA101", "Toronto");
        addFlight("NA102", "Los Angeles");
        addFlight("NA103", "Chicago");
        addFlight("NA104", "Miami");
        addFlight("NA105", "Vancouver");
        addFlight("NA106", "Las Vegas");
        addFlight("NA107", "San Francisco");
        addFlight("NA108", "Montreal");
        addFlight("NA109", "Calgary");
        addFlight("NA110", "Halifax");

        // European Destinations
        addFlight("EU201", "London");
        addFlight("EU202", "Paris");
        addFlight("EU203", "Rome");
        addFlight("EU204", "Barcelona");
        addFlight("EU205", "Amsterdam");
        addFlight("EU206", "Berlin");
        addFlight("EU207", "Athens");
        addFlight("EU208", "Dublin");
        addFlight("EU209", "Vienna");
        addFlight("EU210", "Prague");

        // Asian Destinations
        addFlight("AS301", "Tokyo");
        addFlight("AS302", "Seoul");
        addFlight("AS303", "Beijing");
        addFlight("AS304", "Shanghai");
        addFlight("AS305", "Hong Kong");
        addFlight("AS306", "Singapore");
        addFlight("AS307", "Bangkok");
        addFlight("AS308", "Mumbai");
        addFlight("AS309", "Dubai");
        addFlight("AS310", "Istanbul");
    }

    private void addFlight(String flightNumber, String destination) {
        flights.add(new Flight(flightNumber, ORIGIN, destination));
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }

    public Flight getFlightByNumber(String flightNumber) {
        return flights.stream()
                .filter(f -> f.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public Flight findFlightByNumber(String flightNumber) {
        return flights.stream()
                .filter(flight -> flight.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public Flight findOrCreateFlight(String destination) {
        // Look for existing flight to the destination
        Flight existingFlight = flights.stream()
                .filter(f -> f.getDestination().equalsIgnoreCase(destination) && 
                           f.getBookedSeats() < f.getCapacity())
                .findFirst()
                .orElse(null);

        if (existingFlight != null) {
            return existingFlight;
        }

        // Create new flight if none exists
        String flightNumber = "F" + (flights.size() + 1);
        Flight newFlight = new Flight(flightNumber, ORIGIN, destination);
        flights.add(newFlight);
        return newFlight;
    }
}
