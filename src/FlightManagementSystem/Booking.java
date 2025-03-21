package FlightManagementSystem;

public class Booking {
    private Flight flight;
    private Passenger passenger;
    private String seatNumber;
    private String seatClass;

    public Booking(Flight flight, Passenger passenger, String seatNumber, String seatClass) {
        this.flight = flight;
        this.passenger = passenger;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
    }

    public Flight getFlight() {
        return flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public String generateItinerary(WeatherInfo weatherInfo) {
        StringBuilder itinerary = new StringBuilder();
        itinerary.append("FLIGHT ITINERARY\n");
        itinerary.append("=======================\n\n");
        itinerary.append("Passenger Information:\n");
        itinerary.append(String.format("Name: %s\n", passenger.getName()));
        itinerary.append(String.format("Email: %s\n", passenger.getEmail()));
        itinerary.append(String.format("Phone: %s\n\n", passenger.getPhone()));
        
        itinerary.append("Flight Information:\n");
        itinerary.append(String.format("Flight Number: %s\n", flight.getFlightNumber()));
        itinerary.append(String.format("From: %s\n", flight.getOrigin()));
        itinerary.append(String.format("To: %s\n", flight.getDestination()));
        itinerary.append(String.format("Seat: %s\n", seatNumber));
        itinerary.append(String.format("Class: %s\n\n", seatClass));
        
        itinerary.append("Weather at Destination:\n");
        itinerary.append(String.format("Temperature: %.1f°C\n", weatherInfo.getTemperature()));
        itinerary.append(String.format("Conditions: %s\n\n", weatherInfo.getCondition()));
        
        itinerary.append("Recommended Packing List:\n");
        for (String item : weatherInfo.getPackingList()) {
            itinerary.append(String.format("- %s\n", item));
        }
        
        return itinerary.toString();
    }

    @Override
    public String toString() {
        return String.format("%s - Flight %s to %s", 
            passenger.getName(), flight.getFlightNumber(), flight.getDestination());
    }
}
