package FlightManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FlightManagementGUI extends JFrame {
    private FlightService flightService;
    private BookingService bookingService;
    private WeatherService weatherService;
    private JList<Flight> flightList;
    private DefaultListModel<Flight> flightListModel;
    private JTextArea weatherDisplay;
    private JComboBox<String> seatClassCombo;
    private JComboBox<String> seatRowCombo;
    private JComboBox<String> seatLetterCombo;

    public FlightManagementGUI() {
        weatherService = new WeatherService();
        flightService = new FlightService();
        bookingService = new BookingService(weatherService);

        setTitle("Flight Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Welcome Panel
        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to Fredericton Airport");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);
        add(welcomePanel, BorderLayout.NORTH);

        // Flight List Panel
        JPanel flightPanel = new JPanel(new BorderLayout(5, 5));
        flightPanel.setBorder(BorderFactory.createTitledBorder("Available Flights"));
        
        flightListModel = new DefaultListModel<>();
        flightList = new JList<>(flightListModel);
        flightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        updateFlightList();
        
        flightPanel.add(new JScrollPane(flightList), BorderLayout.CENTER);
        add(flightPanel, BorderLayout.WEST);

        // Booking Panel
        JPanel bookingPanel = new JPanel(new GridBagLayout());
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Book Flight"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Passenger Info
        gbc.gridx = 0; gbc.gridy = 0;
        bookingPanel.add(new JLabel("Name:"), gbc);
        JTextField nameField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        bookingPanel.add(nameField, gbc);

        // Seat Class Selection
        gbc.gridx = 0; gbc.gridy = 1;
        bookingPanel.add(new JLabel("Class:"), gbc);
        seatClassCombo = new JComboBox<>(Flight.getSeatClasses());
        gbc.gridx = 1; gbc.gridy = 1;
        bookingPanel.add(seatClassCombo, gbc);

        // Seat Selection
        gbc.gridx = 0; gbc.gridy = 2;
        bookingPanel.add(new JLabel("Seat:"), gbc);
        JPanel seatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Row selection (1-50)
        String[] rows = new String[50];
        for (int i = 0; i < 50; i++) {
            rows[i] = String.valueOf(i + 1);
        }
        seatRowCombo = new JComboBox<>(rows);
        
        // Letter selection (A-F)
        seatLetterCombo = new JComboBox<>(Flight.getSeatLetters());
        
        seatPanel.add(seatRowCombo);
        seatPanel.add(seatLetterCombo);
        gbc.gridx = 1; gbc.gridy = 2;
        bookingPanel.add(seatPanel, gbc);

        // Book Button
        JButton bookButton = new JButton("Book Flight");
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        bookingPanel.add(bookButton, gbc);

        // Weather Display
        weatherDisplay = new JTextArea(10, 30);
        weatherDisplay.setEditable(false);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        bookingPanel.add(new JScrollPane(weatherDisplay), gbc);

        add(bookingPanel, BorderLayout.CENTER);

        // Event Handlers
        flightList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Flight selectedFlight = flightList.getSelectedValue();
                if (selectedFlight != null) {
                    updateWeatherDisplay(selectedFlight.getDestination());
                }
            }
        });

        bookButton.addActionListener(e -> {
            Flight selectedFlight = flightList.getSelectedValue();
            if (selectedFlight == null) {
                JOptionPane.showMessageDialog(this, "Please select a flight!");
                return;
            }

            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name!");
                return;
            }

            String seatClass = (String) seatClassCombo.getSelectedItem();
            String seatNumber = String.valueOf(seatRowCombo.getSelectedItem()) + 
                              String.valueOf(seatLetterCombo.getSelectedItem());

            Passenger passenger = new Passenger(name, "email@example.com", "555-0123");
            if (bookingService.bookFlight(selectedFlight, passenger, seatNumber, seatClass)) {
                Booking booking = bookingService.getBookings().get(bookingService.getBookings().size() - 1);
                bookingService.saveItinerary(booking);
                JOptionPane.showMessageDialog(this, 
                    "Booking successful!\nSeat: " + seatNumber + 
                    "\nClass: " + seatClass +
                    "\nItinerary has been saved.");
                updateFlightList();
            } else {
                JOptionPane.showMessageDialog(this, "Seat is not available!");
            }
        });

        // Update seat rows based on class selection
        seatClassCombo.addActionListener(e -> {
            String selectedClass = (String) seatClassCombo.getSelectedItem();
            updateSeatRows(selectedClass);
        });
    }

    private void updateSeatRows(String seatClass) {
        seatRowCombo.removeAllItems();
        int startRow, endRow;
        
        switch (seatClass) {
            case "First Class":
                startRow = 1;
                endRow = 5;
                break;
            case "Business":
                startRow = 6;
                endRow = 15;
                break;
            default: // Economy
                startRow = 16;
                endRow = 50;
                break;
        }
        
        for (int i = startRow; i <= endRow; i++) {
            seatRowCombo.addItem(String.valueOf(i));
        }
    }

    private void updateFlightList() {
        flightListModel.clear();
        for (Flight flight : flightService.getAllFlights()) {
            flightListModel.addElement(flight);
        }
    }

    private void updateWeatherDisplay(String destination) {
        WeatherInfo weather = weatherService.getWeather(destination);
        weatherDisplay.setText(weather.toString() + "\n\nRecommended Items to Pack:");
        for (String item : weather.getPackingList()) {
            weatherDisplay.append("\n- " + item);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FlightManagementGUI().setVisible(true);
        });
    }
}
