package FlightManagementSystem;

import java.util.*;

public class FlightService {
    private List<Flight> flights;
    private static final String ORIGIN = "Fredericton";

    public FlightService() {
        flights = new ArrayList<>();
        initializeFlights();
    }

    private void initializeFlights() {
        String[] destinations = {
            "Toronto", "Los Angeles", "Chicago", "Miami", "Vancouver",
            "Las Vegas", "San Francisco", "Montreal", "Calgary", "Halifax",
            "London", "Paris", "Tokyo", "Dubai", "Sydney",
            "Rome", "Amsterdam", "Berlin", "Madrid", "Barcelona",
            "Singapore", "Hong Kong", "Seoul"
        };

        for (String destination : destinations) {
            Flight flight = new Flight("FRE" + (flights.size() + 1), ORIGIN, destination);
            flights.add(flight);
        }
    }

    public List<Flight> getAllFlights() {
        return flights;
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
                .filter(f -> f.getDestination().equalsIgnoreCase(destination))
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
