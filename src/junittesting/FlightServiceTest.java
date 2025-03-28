package junittesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FlightServiceTest {

    FlightService flightService = new FlightService();

    @Test
    void testAvailableFlights() {
        assertEquals(5, flightService.getAvailableFlights().size());
    }

    @Test
    void testFindFlight() {
        assertAll(
            () -> assertEquals("F101", flightService.findFlightByNumber("F101").getFlightNumber()),
            () -> assertEquals("Toronto", flightService.findFlightByNumber("F101").getDestination()),
            () -> assertNull(flightService.findFlightByNumber("INVALID"))
        );
    }

    @Test
    void testFlightDetails() {
        Flight flight = flightService.findFlightByNumber("F102");
        assertAll(
            () -> assertEquals("Fredericton", flight.getOrigin()),
            () -> assertEquals("Vancouver", flight.getDestination()),
            () -> assertEquals(0, flight.getBookedSeats())
        );
    }
    
    @Test
    void testFlightCapacity() {
        Flight flight = flightService.findFlightByNumber("F101");
        assertTrue(flight.getCapacity() > 0);
    }
}