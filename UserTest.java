package com.vacation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(101, "John Doe", "john.doe@example.com", "New York", "123-456-7890");
    }

    @Test
    void testUserCreation() {
        assertEquals(101, user.getUserId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("New York", user.getUserLocation());
        assertEquals("123-456-7890", user.getContactInfo());
    }

    @Test
    void testSetters() {
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setUserLocation("Los Angeles");
        user.setContactInfo("987-654-3210");

        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("Los Angeles", user.getUserLocation());
        assertEquals("987-654-3210", user.getContactInfo());
    }

    @Test
    void testRegister() {
        assertDoesNotThrow(() -> user.register());
    }

    @Test
    void testLogin() {
        assertDoesNotThrow(() -> user.login());
    }

    @Test
    void testUpdateProfile() {
        user.updateProfile("new.email@example.com", "999-999-9999");

        assertEquals("new.email@example.com", user.getEmail());
        assertEquals("999-999-9999", user.getContactInfo());
    }

    @Test
    void testDisplayInvoice() {
        assertDoesNotThrow(() -> user.displayInvoice());
    }
}
