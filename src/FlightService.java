import java.util.ArrayList;
import java.util.List;

public class FlightService {
    private List<Flight> flights;

    public FlightService() {
        this.flights = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
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
        Flight newFlight = new Flight(flightNumber, "Fredericton", destination, 150);
        flights.add(newFlight);
        return newFlight;
    }
}
