package junittesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightTest {
	 private Flight flight;

	    @BeforeEach
	    void setUp() {
	        flight = new Flight("AA123", "New York", "Los Angeles", 150);
	    }

	    @AfterEach
	    void tearDown() {
	        flight = null;
	    }

	    @Test
	    void testConstructorAndGetters() {
	      assertEquals("AA123", flight.getFlightNumber());
	      assertEquals("New York", flight.getOrigin());
	      assertEquals("Los Angeles", flight.getDestination());
	      assertEquals(150, flight.getCapacity());
	      assertEquals(0, flight.getBookedSeats());
	    }

	    @Test
	    void testBookSeat() {
	      assertTrue(flight.bookSeat());
	      assertEquals(1, flight.getBookedSeats());

	      for (int i = 1; i < flight.getCapacity(); i++) {
	            assertTrue(flight.bookSeat());
	       }
	      assertEquals(flight.getCapacity(), flight.getBookedSeats());
	      assertFalse(flight.bookSeat());
	    }

	  @Test
	  void testToString() {
	     String expectedInitial = "Flight AA123 | New York → Los Angeles | Seats: 0/150";
	     assertEquals(expectedInitial, flight.toString());

	     flight.bookSeat();
	     flight.bookSeat();
	     String expectedAfterBooking = "Flight AA123 | New York → Los Angeles | Seats: 2/150";
	     assertEquals(expectedAfterBooking, flight.toString());
	   }
	
	  

}
