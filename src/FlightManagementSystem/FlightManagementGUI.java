package FlightManagementSystem;

import javax.swing.*;
import java.awt.*;

public class FlightManagementGUI {
    private final JFrame frame;
    private final FlightService flightService;
    private final BookingService bookingService;
    private final WeatherService weatherService;
    private final JList<Flight> flightsList;
    private final JList<Booking> bookingsList;

    public FlightManagementGUI() {
        flightService = new FlightService();
        bookingService = new BookingService();
        weatherService = new WeatherService("dummy-key"); // Mock implementation doesn't use the key
        flightsList = new JList<>();
        bookingsList = new JList<>();
        frame = new JFrame("Flight Management System");
        initialize();
        addSampleData(); // Add some sample data for testing
    }

    private void addSampleData() {
        // Add sample flights
        flightService.addFlight(new Flight("F1", "Fredericton", "Toronto", 150));
        flightService.addFlight(new Flight("F2", "Fredericton", "Montreal", 120));
        flightService.addFlight(new Flight("F3", "Fredericton", "Halifax", 100));
        
        // Update the flights list
        updateFlightsList();
    }

    private void updateFlightsList() {
        DefaultListModel<Flight> model = new DefaultListModel<>();
        for (Flight flight : flightService.getAvailableFlights()) {
            model.addElement(flight);
        }
        flightsList.setModel(model);
    }

    private void updateBookingsList() {
        DefaultListModel<Booking> model = new DefaultListModel<>();
        for (Booking booking : bookingService.getAllBookings()) {
            model.addElement(booking);
        }
        bookingsList.setModel(model);
    }

    private void initialize() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Create main panels
        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel bottomPanel = createBottomPanel();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        JLabel titleLabel = new JLabel("Flight Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);
        
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        // Flights Panel
        JPanel flightsPanel = new JPanel();
        flightsPanel.setBorder(BorderFactory.createTitledBorder("Available Flights"));
        flightsPanel.add(new JScrollPane(flightsList));

        // Bookings Panel
        JPanel bookingsPanel = new JPanel();
        bookingsPanel.setBorder(BorderFactory.createTitledBorder("Current Bookings"));
        bookingsPanel.add(new JScrollPane(bookingsList));

        panel.add(flightsPanel);
        panel.add(bookingsPanel);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton bookFlightButton = new JButton("Book Flight");
        JButton checkWeatherButton = new JButton("Check Weather");
        JButton refreshButton = new JButton("Refresh");

        bookFlightButton.addActionListener(e -> bookFlight());
        checkWeatherButton.addActionListener(e -> checkWeather());
        refreshButton.addActionListener(e -> refreshData());

        panel.add(bookFlightButton);
        panel.add(checkWeatherButton);
        panel.add(refreshButton);

        return panel;
    }

    private void bookFlight() {
        Flight selectedFlight = flightsList.getSelectedValue();
        if (selectedFlight == null) {
            JOptionPane.showMessageDialog(frame, "Please select a flight first!");
            return;
        }

        // Simple dialog to get passenger details
        String name = JOptionPane.showInputDialog(frame, "Enter passenger name:");
        if (name != null && !name.trim().isEmpty()) {
            Passenger passenger = new Passenger(name, "email@example.com", "555-0123");
            bookingService.bookFlight(selectedFlight, passenger);
            updateBookingsList();
            updateFlightsList();
        }
    }

    private void checkWeather() {
        Flight selectedFlight = flightsList.getSelectedValue();
        if (selectedFlight == null) {
            JOptionPane.showMessageDialog(frame, "Please select a flight to check weather!");
            return;
        }

        WeatherInfo weather = weatherService.getWeather(selectedFlight.getDestination());
        StringBuilder message = new StringBuilder();
        message.append("Weather in ").append(weather.getDestination()).append(":\n");
        message.append(String.format("Temperature: %.1f°C\n", weather.getTemperature()));
        message.append("Condition: ").append(weather.getCondition()).append("\n\n");
        message.append("Packing Recommendations:\n");
        for (String recommendation : weather.getPackingRecommendations()) {
            message.append("- ").append(recommendation).append("\n");
        }
        
        JOptionPane.showMessageDialog(frame, message.toString());
    }

    private void refreshData() {
        updateFlightsList();
        updateBookingsList();
    }

    public void show() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            FlightManagementGUI gui = new FlightManagementGUI();
            gui.show();
        });
    }
}
