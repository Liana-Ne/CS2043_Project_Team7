# CS2043_Project_Team7
Vacation Management Software - planning flights, accommodations, and itineraries

Features:

User.java (Main Class)

    Represents a user with attributes such as ID, name, email, location, and contact information.
    Supports user actions including registration, login, and profile updates.
    Generates a simple invoice displaying user details.

UserTest.java (JUnit Test Class)

    Validates the User class using JUnit 5.
    Ensures the correctness of user actions like registration, login, and profile updates.
    Captures and verifies console outputs for expected results.

Added all the unit test classes in the unit-testing branch:
    Task of each test class:
    
    PackingListTest class: Checks if essential items (like passport, clothes) are included. 
                           It ensures weather based packing. 
                           It also generates a packing list depending the trip duration.
                           
    FlightTest class: Tests the Flight class to ensure flights are created and managed correctly.
                      Checks if a flight is created with the correct number, origin, destination, and capacity.
                      Tests if seats can be booked successfully.
                      Ensures no overbooking happens (fails when flight is full).
                      
