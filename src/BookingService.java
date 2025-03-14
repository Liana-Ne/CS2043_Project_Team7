

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings;

    public BookingService() {
        this.bookings = new ArrayList<>();
    }

    public void bookFlight(Flight flight, Passenger passenger) {
        if (flight.bookSeat()) {
            bookings.add(new Booking(flight, passenger));
            System.out.println("Booking successful!");
        } else {
            System.out.println("Flight is full! Cannot book.");
        }
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}
