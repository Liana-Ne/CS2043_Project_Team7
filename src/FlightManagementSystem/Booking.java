package FlightManagementSystem;

public class Booking {
    private Flight flight;
    private Passenger passenger;

    public Booking(Flight flight, Passenger passenger) {
        this.flight = flight;
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    @Override
    public String toString() {
        return String.format("%s - Flight %s to %s", 
            passenger.getName(), flight.getFlightNumber(), flight.getDestination());
    }
}
