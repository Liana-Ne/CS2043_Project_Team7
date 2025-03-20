package junittesting;

import java.util.ArrayList;
import java.util.List;

public class FlightService {
    private List<Flight> flights;

    public FlightService() {
        this.flights = new ArrayList<>();
        initializeFlights();
    }

    private void initializeFlights() {
        flights.add(new Flight("F101", "Fredericton", "Toronto", 5));
        flights.add(new Flight("F102", "Fredericton", "Vancouver", 3));
        flights.add(new Flight("F103", "Fredericton", "Montreal", 4));
        flights.add(new Flight("F104", "Fredericton", "Calgary", 2));
        flights.add(new Flight("F105", "Fredericton", "Halifax", 6));
    }

    public List<Flight> getAvailableFlights() {
        return flights;
    }

    public Flight findFlightByNumber(String flightNumber) {
        return flights.stream()
                .filter(flight -> flight.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);
    }
}
