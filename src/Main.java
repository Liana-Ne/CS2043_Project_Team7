
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightService flightService = new FlightService();
        BookingService bookingService = new BookingService();

        System.out.println("\nWelcome to the Flight Management System!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View Available Flights from Fredericton");
            System.out.println("2. Book a Flight");
            System.out.println("3. View Bookings");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Flights from Fredericton:");
                    List<Flight> availableFlights = flightService.getAvailableFlights();
                    for (Flight flight : availableFlights) {
                        System.out.println(flight);
                    }
                    break;

                case 2:
                    System.out.print("Enter your Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Passport Number: ");
                    String passportNumber = scanner.nextLine();
                    System.out.print("Enter Flight Number to book: ");
                    String flightNumToBook = scanner.nextLine();

                    Flight selectedFlight = flightService.findFlightByNumber(flightNumToBook);
                    if (selectedFlight != null) {
                        Passenger passenger = new Passenger(name, passportNumber);
                        bookingService.bookFlight(selectedFlight, passenger);
                    } else {
                        System.out.println("Flight not found!");
                    }
                    break;

                case 3:
                    System.out.println("\nAll Bookings:");
                    for (Booking booking : bookingService.getAllBookings()) {
                        System.out.println(booking);
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
