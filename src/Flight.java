

public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private int capacity;
    private int bookedSeats;

    public Flight(String flightNumber, String origin, String destination, int capacity) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.capacity = capacity;
        this.bookedSeats = 0;
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

    public int getCapacity() {
        return capacity;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public boolean bookSeat() {
        if (bookedSeats < capacity) {
            bookedSeats++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Flight " + flightNumber + " | " + origin + " → " + destination + " | Seats: "
                + bookedSeats + "/" + capacity;
    }
}
