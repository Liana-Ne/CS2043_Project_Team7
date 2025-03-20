package junittesting;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a user in the vacation management system.
 * This class gathers and manages information about the user's trip preferences,
 * including personal details, trip length, seat type, flight class, and in-flight amenities.
 * It also provides functionality to display user details and generate an itinerary file.
 */
public class User {
    private int userId;
    private String name;
    private String email;
    private String userLocation;
    private String contactInfo;
    private int tripLength; // Length of the trip in days
    private String seatType; // Window, Aisle, Bulkhead
    private String flightClass; // Economy, Premium Economy, Business, First Class
    private boolean hasWifi; // In-flight Wi-Fi preference
    private boolean hasEntertainment; // In-flight entertainment preference
    private String foodQuality; // Food and beverage quality preference

    /**
     * Constructs a new User with the specified details.
     * @param userId        The unique identifier for the user.
     * @param name          The name of the user.
     * @param email         The email address of the user.
     * @param userLocation  The location of the user.
     * @param contactInfo   The contact information of the user.
     */
    public User(int userId, String name, String email, String userLocation, String contactInfo) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userLocation = userLocation;
        this.contactInfo = contactInfo;
    }

    // Getter and Setter Methods

    /**
     * Returns the user's unique identifier.
     * @return The user ID.
     */
    public int getUserId() { 
        return userId;
    }

    /**
     * Returns the user's name.
     * @return The name of the user.
     */
    public String getName() {
        return name; 
    }

    /**
     * Sets the user's name.
     * @param name The new name of the user.
     */
    public void setName(String name) { 
        this.name = name; 
    }

    /**
     * Returns the user's email address.
     * @return The email address of the user.
     */
    public String getEmail() { 
        return email; 
    }

    /**
     * Sets the user's email address.
     * @param email The new email address of the user.
     */
    public void setEmail(String email) { 
        this.email = email; 
    }

    /**
     * Returns the user's location.
     * @return The location of the user.
     */
    public String getUserLocation() { 
        return userLocation; 
    }

    /**
     * Sets the user's location.
     * @param userLocation The new location of the user.
     */
    public void setUserLocation(String userLocation) { 
        this.userLocation = userLocation; 
    }

    /**
     * Returns the user's contact information.
     * @return The contact information of the user.
     */
    public String getContactInfo() { 
        return contactInfo; 
    }

    /**
     * Sets the user's contact information.
     * @param contactInfo The new contact information of the user.
     */
    public void setContactInfo(String contactInfo) { 
        this.contactInfo = contactInfo;
    }

    /**
     * Returns the length of the user's trip in days.
     * @return The trip length in days.
     */
    public int getTripLength() {
        return tripLength;
    }

    /**
     * Sets the length of the user's trip in days.
     * @param tripLength The trip length in days.
     */
    public void setTripLength(int tripLength) {
        this.tripLength = tripLength;
    }

    /**
     * Returns the user's preferred seat type.
     * @return The preferred seat type (Window, Aisle, Bulkhead).
     */
    public String getSeatType() {
        return seatType;
    }

    /**
     * Sets the user's preferred seat type.
     * @param seatType The preferred seat type (Window, Aisle, Bulkhead).
     */
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    /**
     * Returns the user's preferred flight class.
     * @return The preferred flight class (Economy, Premium Economy, Business, First Class).
     */
    public String getFlightClass() {
        return flightClass;
    }

    /**
     * Sets the user's preferred flight class.
     * @param flightClass The preferred flight class (Economy, Premium Economy, Business, First Class).
     */
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    /**
     * Returns whether the user wants in-flight Wi-Fi.
     * @return True if the user wants Wi-Fi, false otherwise.
     */
    public boolean hasWifi() {
        return hasWifi;
    }

    /**
     * Sets whether the user wants in-flight Wi-Fi.
     * @param hasWifi True if the user wants Wi-Fi, false otherwise.
     */
    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    /**
     * Returns whether the user wants in-flight entertainment.
     * @return True if the user wants entertainment, false otherwise.
     */
    public boolean hasEntertainment() {
        return hasEntertainment;
    }

    /**
     * Sets whether the user wants in-flight entertainment.
     * @param hasEntertainment True if the user wants entertainment, false otherwise.
     */
    public void setHasEntertainment(boolean hasEntertainment) {
        this.hasEntertainment = hasEntertainment;
    }

    /**
     * Returns the user's preferred food and beverage quality.
     * @return The preferred food quality (Standard, Premium).
     */
    public String getFoodQuality() {
        return foodQuality;
    }

    /**
     * Sets the user's preferred food and beverage quality.
     * @param foodQuality The preferred food quality (Standard, Premium).
     */
    public void setFoodQuality(String foodQuality) {
        this.foodQuality = foodQuality;
    }

    /**
     * Sets the length of the user's trip in days.
     * @param tripLength The trip length in days.
     */
    public void setTripDetails(int tripLength) {
        this.tripLength = tripLength;
    }

    /**
     * Sets the user's preferred seat type.
     * @param seatType The preferred seat type (Window, Aisle, Bulkhead).
     */
    public void setSeatPreference(String seatType) {
        this.seatType = seatType;
    }

    /**
     * Sets the user's preferred flight class.
     * @param flightClass The preferred flight class (Economy, Premium Economy, Business, First Class).
     */
    public void setFlightClassPreference(String flightClass) {
        this.flightClass = flightClass;
    }

    /**
     * Sets the user's preferences for in-flight amenities.
     * @param hasWifi Whether the user wants Wi-Fi.
     * @param hasEntertainment Whether the user wants in-flight entertainment.
     * @param foodQuality The preferred food and beverage quality (Standard, Premium).
     */
    public void setInFlightAmenities(boolean hasWifi, boolean hasEntertainment, String foodQuality) {
        this.hasWifi = hasWifi;
        this.hasEntertainment = hasEntertainment;
        this.foodQuality = foodQuality;
    }

    /**
     * Displays all user details and preferences in a formatted manner.
     */
    public void displayUserDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("                  USER DETAILS                    ");
        System.out.println("--------------------------------------------------");
        System.out.println("User ID       : " + userId);
        System.out.println("Name          : " + name);
        System.out.println("Email         : " + email);
        System.out.println("Location      : " + userLocation);
        System.out.println("Contact Info  : " + contactInfo);
        System.out.println("Trip Length   : " + tripLength + " days");
        System.out.println("Seat Type     : " + seatType);
        System.out.println("Flight Class  : " + flightClass);
        System.out.println("Wi-Fi         : " + (hasWifi ? "Yes" : "No"));
        System.out.println("Entertainment : " + (hasEntertainment ? "Yes" : "No"));
        System.out.println("Food Quality  : " + foodQuality);
        System.out.println("--------------------------------------------------");
    }

    	/**
	 * Generates a text file with the user's itinerary. If the file already exists,
	 * the new itinerary will be appended to the file.
	 * @param fileName The name of the file to save the itinerary.
	 */
	public void generateItinerary(String fileName) {
	    try (FileWriter writer = new FileWriter(fileName, true)) {
		writer.write("--------------------------------------------------\n");
		writer.write("                  USER ITINERARY                   \n");
		writer.write("--------------------------------------------------\n");
		writer.write("User ID       : " + userId + "\n");
		writer.write("Name          : " + name + "\n");
		writer.write("Email         : " + email + "\n");
		writer.write("Location      : " + userLocation + "\n");
		writer.write("Contact Info  : " + contactInfo + "\n");
		writer.write("Trip Length   : " + tripLength + " days\n");
		writer.write("Seat Type     : " + seatType + "\n");
		writer.write("Flight Class  : " + flightClass + "\n");
		writer.write("Wi-Fi         : " + (hasWifi ? "Yes" : "No") + "\n");
		writer.write("Entertainment : " + (hasEntertainment ? "Yes" : "No") + "\n");
		writer.write("Food Quality  : " + foodQuality + "\n");
		writer.write("--------------------------------------------------\n");
		writer.write("Thank you for choosing our services!\n");
		writer.write("--------------------------------------------------\n\n");
		System.out.println("Itinerary saved to " + fileName);
	    } catch (IOException e) {
		System.out.println("An error occurred while saving the itinerary.");
		e.printStackTrace();
	    }
	}
	
	 /**
	 * Displays the itinerary for a specific user from the itinerary file.
	 * @param fileName The name of the file to read from.
	 * @param name The name of the user to search for.
	 */
	public static void showItineraryForUser(String fileName, String name) {
	    try (FileReader fileReader = new FileReader(fileName)) {
		boolean found = false;
		boolean isUserItinerary = false;
		int character;
		StringBuilder currentLine = new StringBuilder();

		while ((character = fileReader.read()) != -1) {

		    if (character != '\n') {
		        currentLine.append((char) character);
		    } else {

		        if (currentLine.toString().contains("Name          : " + name)) {
		            found = true;
		            isUserItinerary = true;
		        }

		        if (isUserItinerary) {
		            System.out.println(currentLine.toString());
		        }
                
		        currentLine.setLength(0);

		        if (isUserItinerary && currentLine.toString().contains("--------------------------------------------------")) {
		            isUserItinerary = false;
		            break; 
		        }
		    }
		}

		if (!found) {
		    System.out.println("No itinerary found for user: " + name);
		}
	    } catch (IOException e) {
		System.out.println("An error occurred while reading the itinerary file.");
		e.printStackTrace();
	    }
	}
	
	
}
