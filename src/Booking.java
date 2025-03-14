

public class Booking {
    private Flight flight;
    private Passenger passenger;

    public Booking(Flight flight, Passenger passenger) {
        this.flight = flight;
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return passenger + " booked " + flight;
    }
}
