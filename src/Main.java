import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightService flightService = new FlightService();
        BookingService bookingService = new BookingService();
        WeatherService weatherService = new WeatherService(WeatherConfig.getApiKey());

        System.out.println("\nWelcome!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Plan a New Trip");
            System.out.println("2. View My Bookings");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    planNewTrip(scanner, weatherService, flightService, bookingService);
                    break;

                case 2:
                    System.out.println("\nYour Bookings:");
                    for (Booking booking : bookingService.getAllBookings()) {
                        System.out.println(booking);
                        try {
                            // Show current weather at the destination
                            Flight flight = booking.getFlight();
                            WeatherInfo weatherInfo = weatherService.getWeather(flight.getDestination());
                            System.out.println("Current weather at destination:");
                            System.out.println(weatherInfo);
                            System.out.println("------------------------");
                        } catch (Exception e) {
                            System.out.println("Unable to fetch current weather: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using our service. Have a great trip!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void planNewTrip(Scanner scanner, WeatherService weatherService, 
                                  FlightService flightService, BookingService bookingService) {
        System.out.println("\nLet's plan your trip from Fredericton!");
        
        // Get destination
        System.out.print("Where would you like to go? (Enter city name): ");
        String destination = scanner.nextLine();

        // Check weather at destination
        try {
            WeatherInfo weatherInfo = weatherService.getWeather(destination);
            System.out.println("\nCurrent weather at " + destination + ":");
            System.out.println(weatherInfo);
            
            // If weather info looks good, proceed with booking
            System.out.println("\nWould you like to book a flight to " + destination + "? (yes/no): ");
            String bookChoice = scanner.nextLine();
            
            if (bookChoice.equalsIgnoreCase("yes")) {
                // Get or create a flight for this destination
                Flight flight = flightService.findOrCreateFlight(destination);
                
                // Get passenger details
                System.out.print("Enter your name: ");
                String name = scanner.nextLine();
                System.out.print("Enter your passport number: ");
                String passportNumber = scanner.nextLine();
                
                Passenger passenger = new Passenger(name, passportNumber);
                bookingService.bookFlight(flight, passenger);
                
                System.out.println("\nBooking confirmed!");
                System.out.println("Flight details: " + flight);
                System.out.println("\nPacking Recommendations:");
                System.out.println("Based on the weather, you should pack:");
                for (String item : weatherInfo.getPackingRecommendations()) {
                    System.out.println("- " + item);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
