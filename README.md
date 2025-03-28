# Flight Management System GUI

A Java Swing desktop application demonstrating a user interface for browsing and booking flights.

## Overview

This project simulates a flight booking experience through a graphical user interface. Users can view a list of available flights, input their details, select a seat from a visual layout, declare items, and receive a booking confirmation complete with simulated weather information and a packing list for their destination.

## Key Features

*   **Flight Browsing:** Displays a list of available flights with details like flight number and destination.
*   **Booking Form:** Allows users to input:
    *   First and Last Name (with basic validation)
    *   Departure and Return Dates (using a custom `DateTextField` with YYYY/MM/DD format validation, placeholder text, and date logic checks)
    *   Seat Class preference
*   **Visual Seat Selection:**
    *   Presents a scrollable seat map divided into First Class, Economy, and Business sections.
    *   Uses custom `SeatButton` components to show seat numbers and handle selection state visually.
    *   **Note:** Seat availability is currently *simulated* for demonstration purposes based on the flight number, not real-time booking data. Unavailable seats are visually disabled.
*   **Declarations:** Provides checkboxes for declaring common items like tobacco, alcohol, or weapons.
*   **Booking Confirmation:**
    *   Displays a detailed confirmation dialog upon successful booking.
    *   Includes passenger details, flight information, selected seat, and dates.
    *   Shows simulated weather information for the destination (provided by `WeatherService`).
    *   Generates a recommended packing list based on the simulated weather.
    *   Lists any items declared by the user.
*   **Print Itinerary:** Allows the user to save the booking confirmation details (including weather and packing list) to a local text file (e.g., `itinerary_FL123_john_doe.txt`).

## Technical Details

*   **Language:** Java
*   **Framework:** Swing (for the GUI)
*   **Architecture:** Interacts with service layers (`FlightService`, `BookingService`, `WeatherService`) to handle backend logic (though the implementations of these services are separate). Uses custom Swing components (`SeatButton`, `DateTextField`, `StyledTextField`) for specific UI elements and validation.

## Running the Application

1.  **Compile the Java code:**
    Navigate to the `src` directory in your terminal.
    ```bash
    javac FlightManagementSystem/*.java
    ```
    *(Adjust command based on your project structure/build system if needed)*

2.  **Run the application:**
    From the `src` directory:
    ```bash
    java FlightManagementSystem.FlightManagementGUI
    ```
