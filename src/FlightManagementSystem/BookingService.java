package FlightManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class BookingService {
    private List<Booking> bookings;
    private WeatherService weatherService;

    public BookingService(WeatherService weatherService) {
        this.bookings = new ArrayList<>();
        this.weatherService = weatherService;
    }

    public boolean bookFlight(Flight flight, Passenger passenger, String seatNumber, String seatClass) {
        if (flight.bookSeat(seatNumber, seatClass)) {
            Booking booking = new Booking(flight, passenger, seatNumber, seatClass);
            bookings.add(booking);
            return true;
        }
        return false;
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    public void saveItinerary(Booking booking) {
        WeatherInfo weatherInfo = weatherService.getWeather(booking.getFlight().getDestination());
        String itinerary = booking.generateItinerary(weatherInfo);
        
        String fileName = String.format("itinerary_%s_%s.txt",
            booking.getPassenger().getName().replaceAll("\\s+", "_"),
            booking.getFlight().getFlightNumber());
            
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(itinerary);
            System.out.println("Itinerary saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving itinerary: " + e.getMessage());
        }
    }
}
