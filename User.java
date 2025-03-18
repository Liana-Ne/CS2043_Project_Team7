package com.vacation;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a user in the vacation management system.
 */
class User {
    private int userId;
    private String name;
    private String email;
    private String userLocation;
    private String contactInfo;

    /**
     * Constructs a new User.
     *
     * @param userId        :The unique identifier for the user.
     * @param name          :The name of the user.
     * @param email         :The email address of the user.
     * @param userLocation  :The location of the user.
     * @param contactInfo   ;The contact information of the user.
     */
    public User(int userId, String name, String email, String userLocation, String contactInfo) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userLocation = userLocation;
        this.contactInfo = contactInfo;
    }

    // Getter and Setter Methods
    public int getUserId() { 
    	return userId;
    }
    public String getName() {
    	return name; 
    }
    public String getEmail() { 
    	return email; 
    }
    public String getUserLocation() { 
    	return userLocation; 
    }
    public String getContactInfo() { 
    	return contactInfo; 
    }
    public void setName(String name) { 
    	this.name = name; 
    }
    public void setEmail(String email) { 
    	this.email = email; 
    }
    public void setUserLocation(String userLocation) { 
    	this.userLocation = userLocation; 
    }
    public void setContactInfo(String contactInfo) { 
    	this.contactInfo = contactInfo ;
    }

    /**
     * Registers the user.
     */
    public void register() {
        System.out.println("User registered: " + name);
    }

    /**
     * Logs the user in.
     */
    public void login() {
        System.out.println("User logged in: " + email);
    }

    /**
     * Updates the user profile with new email and contact information.
     * @param newEmail, The new email address.
     * @param newContactInfo The new contact information.
     */
    public void updateProfile(String newEmail, String newContactInfo) {
        this.email = newEmail;
        this.contactInfo = newContactInfo;
        System.out.println("Profile updated for: " + name);
    }

    /**
     * Displays the invoice for the user.
     */
    public void displayInvoice() {
        System.out.println("--------------------------------------------------");
        System.out.println("                  INVOICE                         ");
        System.out.println("--------------------------------------------------");
        System.out.println("User ID       : " + userId);
        System.out.println("Name          : " + name);
        System.out.println("Email         : " + email);
        System.out.println("Location      : " + userLocation);
        System.out.println("Contact Info  : " + contactInfo);
        System.out.println("--------------------------------------------------");
        System.out.println("Amount Due    : $XXX.XX (Placeholder for amount)");
        System.out.println("--------------------------------------------------");
        System.out.println("Thank you for using our services!");
        System.out.println("--------------------------------------------------");
    }
}

