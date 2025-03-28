package FlightManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class FlightManagementGUI extends JFrame {
    private FlightService flightService;
    private BookingService bookingService;
    private WeatherService weatherService;
    private JList<Flight> flightList;
    private DefaultListModel<Flight> flightListModel;
    private JComboBox<String> seatClassCombo;
    private JButton selectedSeatButton;
    private JPanel mainContentPanel;
    private JPanel seatGridPanel;

    // Custom button class to ensure colors are properly displayed
    private static class SeatButton extends JButton {
        private boolean isSelected = false;

        public SeatButton() {
            super();
            setOpaque(true);
            setBorderPainted(true);
            setBackground(Color.WHITE);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!isSelected) {
                        setBackground(new Color(240, 240, 255)); // Light hover color
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!isSelected) {
                        setBackground(Color.WHITE);
                    }
                }
            });
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;
            if (selected) {
                setBackground(new Color(200, 255, 200)); // Light green for selection
                setForeground(new Color(0, 100, 0)); // Dark green text
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
        }

        public boolean isButtonSelected() {
            return isSelected;
        }
    }

    public FlightManagementGUI() {
        weatherService = new WeatherService();
        flightService = new FlightService();
        bookingService = new BookingService(weatherService);

        // Initialize combo boxes
        seatClassCombo = new JComboBox<>(Flight.getSeatClasses());

        // Color scheme
        Color primaryColor = new Color(79, 40, 114);  // Deep purple
        Color secondaryColor = new Color(245, 245, 250);  // Light gray-purple
        Color accentColor = new Color(144, 95, 192);  // Light purple
        Color textColor = new Color(51, 51, 51);  // Dark gray
        Color buttonHoverColor = new Color(89, 50, 124);  // Slightly darker purple

        setTitle("Flight Management System");
        setSize(1300, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(secondaryColor);

        // Welcome Panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(primaryColor);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(17, 0, 17, 0));
        JLabel welcomeLabel = new JLabel("Welcome to Fredericton Airport");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 31));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel);
        add(welcomePanel, BorderLayout.NORTH);

        // Flight List Panel
        JPanel flightPanel = new JPanel(new BorderLayout(5, 5));
        flightPanel.setBackground(secondaryColor);
        JLabel flightTitle = new JLabel("Available Flights", SwingConstants.CENTER);
        flightTitle.setFont(new Font("Arial", Font.BOLD, 26));
        flightTitle.setForeground(primaryColor);
        flightTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 11, 0));
        flightPanel.add(flightTitle, BorderLayout.NORTH);
        
        flightListModel = new DefaultListModel<>();
        flightList = new JList<>(flightListModel);
        flightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightList.setFont(new Font("Arial", Font.PLAIN, 20));
        flightList.setFixedCellHeight(55);
        flightList.setBackground(Color.WHITE);
        flightList.setVisibleRowCount(8); // Set visible row count
        flightList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                if (isSelected) {
                    label.setBackground(accentColor);
                    label.setForeground(Color.WHITE);
                } else {
                    label.setForeground(textColor);
                }
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                return label;
            }
        });
        updateFlightList();
        
        JScrollPane flightScrollPane = new JScrollPane(flightList);
        flightScrollPane.setPreferredSize(new Dimension(325, 650));
        flightScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        flightScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        flightScrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        flightScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        flightPanel.add(flightScrollPane, BorderLayout.CENTER);
        add(flightPanel, BorderLayout.WEST);

        // Main Content Panel
        mainContentPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        mainContentPanel.setBackground(secondaryColor);

        // Booking Panel
        JPanel bookingPanel = new JPanel(new GridBagLayout());
        bookingPanel.setBackground(Color.WHITE);
        bookingPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(0, 20, 20, 20)  // Reduced top padding from 20 to 0
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        
        // Book Flight Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 11, 0));
        JLabel bookingTitle = new JLabel("Book Flight", SwingConstants.CENTER);
        bookingTitle.setFont(new Font("Arial", Font.BOLD, 26));  // Increased from 24 to 26 to match other titles
        bookingTitle.setForeground(primaryColor);
        titlePanel.add(bookingTitle);
        gbc.gridx = 0;
        gbc.gridy = 0;
        bookingPanel.add(titlePanel, gbc);

        // Reset gridwidth for other components
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 5, 5, 5);  // Added more top padding for first row after title
        
        // Inner class for styled text fields
        class StyledTextField extends JTextField {
            StyledTextField(int columns) {
                super(columns);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, accentColor),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                setBackground(new Color(252, 252, 255));
            }
        }

        // Inner class for styled date fields with validation
        class DateTextField extends StyledTextField {
            private String placeholder;
            private boolean isPlaceholderShowing;
            
            DateTextField(int columns, String placeholder) {
                super(columns);
                this.placeholder = placeholder;
                this.isPlaceholderShowing = true;
                setText(placeholder);
                setForeground(Color.GRAY);
                
                // Add focus listeners for placeholder behavior
                addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (isPlaceholderShowing) {
                            setText("");
                            setForeground(textColor);
                            isPlaceholderShowing = false;
                        }
                    }
                    
                    @Override
                    public void focusLost(FocusEvent e) {
                        if (getText().isEmpty()) {
                            setText(placeholder);
                            setForeground(Color.GRAY);
                            isPlaceholderShowing = true;
                        }
                    }
                });
            }
            
            public boolean isValidDate() {
                if (isPlaceholderShowing || getText().trim().equals(placeholder)) {
                    JOptionPane.showMessageDialog(this,
                        "Please enter a date",
                        "Missing Date",
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                String text = getText().trim();
                
                // First check the format
                if (!text.matches("\\d{4}/\\d{2}/\\d{2}")) {
                    JOptionPane.showMessageDialog(this,
                        "Please enter the date in YYYY/MM/DD format",
                        "Invalid Date Format",
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                try {
                    String[] parts = text.split("/");
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);
                    
                    // Validate year (2025 or later)
                    if (year < 2025) {
                        JOptionPane.showMessageDialog(this,
                            "Year must be 2025 or later",
                            "Invalid Year",
                            JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    
                    // Validate month (1-12)
                    if (month < 1 || month > 12) {
                        JOptionPane.showMessageDialog(this,
                            "Month must be between 1 and 12",
                            "Invalid Month",
                            JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    
                    // Validate days based on month
                    int maxDays;
                    if (month == 2) {
                        // February - check for leap year
                        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                        maxDays = isLeapYear ? 29 : 28;
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        // April, June, September, November have 30 days
                        maxDays = 30;
                    } else {
                        // All other months have 31 days
                        maxDays = 31;
                    }
                    
                    if (day < 1 || day > maxDays) {
                        JOptionPane.showMessageDialog(this,
                            "Invalid day for the selected month",
                            "Invalid Day",
                            JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    
                    return true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                        "Please enter a valid date",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            
            public String getDate() {
                return isPlaceholderShowing || getText().trim().equals(placeholder) ? "" : getText().trim();
            }
        }

        // Labels and Fields
        Font labelFont = new Font("Arial", Font.BOLD, 15);
        Font fieldFont = new Font("Arial", Font.PLAIN, 15);
        
        // Form fields
        String[] labels = {"First Name:", "Last Name:", "Departure Date:", "Return Date:", "Class:"};
        Object[] fields = new Object[5]; // Use Object array for different field types
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            label.setForeground(textColor);
            bookingPanel.add(label, gbc);

            gbc.gridx = 1;
            if (i < 2) {
                // First Name, Last Name
                fields[i] = new StyledTextField(12);
                ((JTextField)fields[i]).setFont(fieldFont);
                bookingPanel.add((Component)fields[i], gbc);
            } else if (i < 4) {
                // Departure Date, Return Date
                fields[i] = new DateTextField(12, "YYYY/MM/DD");
                ((JTextField)fields[i]).setFont(fieldFont);
                bookingPanel.add((Component)fields[i], gbc);
            } else {
                // Class
                fields[i] = seatClassCombo;
                seatClassCombo.setFont(fieldFont);
                seatClassCombo.setBackground(Color.WHITE);
                bookingPanel.add(seatClassCombo, gbc);
            }
        }

        // Declarations Section
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JLabel declarationsLabel = new JLabel("Declarations:", SwingConstants.LEFT);
        declarationsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        declarationsLabel.setForeground(primaryColor);
        bookingPanel.add(declarationsLabel, gbc);

        // Checkboxes
        Font checkboxFont = new Font("Arial", Font.PLAIN, 14);
        String[] checkboxLabels = {"Tobacco products", "Alcohol", "Weapons or incendiaries"};
        JCheckBox[] checkboxes = new JCheckBox[3];
        
        for (int i = 0; i < checkboxLabels.length; i++) {
            gbc.gridy = 7 + i;
            checkboxes[i] = new JCheckBox(checkboxLabels[i]);
            checkboxes[i].setFont(checkboxFont);
            checkboxes[i].setBackground(Color.WHITE);
            checkboxes[i].setForeground(textColor);
            bookingPanel.add(checkboxes[i], gbc);
        }

        // Book Button
        gbc.gridy = 10;
        gbc.insets = new Insets(22, 6, 6, 6);
        JButton bookButton = new JButton("Book Flight");
        bookButton.setFont(new Font("Arial", Font.BOLD, 15));
        bookButton.setBackground(primaryColor);
        bookButton.setForeground(Color.WHITE);
        bookButton.setBorder(BorderFactory.createEmptyBorder(11, 22, 11, 22));
        bookButton.setFocusPainted(false);
        bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bookButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                bookButton.setBackground(buttonHoverColor);
            }
            public void mouseExited(MouseEvent e) {
                bookButton.setBackground(primaryColor);
            }
        });
        bookingPanel.add(bookButton, gbc);

        // Seat Grid Panel
        seatGridPanel = new JPanel(new BorderLayout(5, 5));
        seatGridPanel.setBackground(Color.WHITE);
        seatGridPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel seatTitle = new JLabel("Select Your Seat", SwingConstants.CENTER);
        seatTitle.setFont(new Font("Arial", Font.BOLD, 24));
        seatTitle.setForeground(primaryColor);
        seatTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        seatGridPanel.add(seatTitle, BorderLayout.NORTH);
        
        JScrollPane seatGrid = createSeatGrid(primaryColor, accentColor);
        seatGridPanel.add(seatGrid, BorderLayout.CENTER);

        // Add panels to main content
        mainContentPanel.add(bookingPanel);
        mainContentPanel.add(seatGridPanel);
        
        add(mainContentPanel, BorderLayout.CENTER);

        // Event Handlers
        flightList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSeatDisplay();
            }
        });

        bookButton.addActionListener(e -> {
            Flight selectedFlight = flightList.getSelectedValue();
            if (selectedFlight == null) {
                JOptionPane.showMessageDialog(this, "Please select a flight!");
                return;
            }

            String firstName = ((JTextField)fields[0]).getText().trim();
            String lastName = ((JTextField)fields[1]).getText().trim();
            if (firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your full name!");
                return;
            }

            // Validate dates entered
            DateTextField departureField = (DateTextField)fields[2];
            DateTextField returnField = (DateTextField)fields[3];
            
            if (!departureField.isValidDate()) {
                return;
            }
            if (!returnField.isValidDate()) {
                return;
            }

            // Compare dates to ensure return is after departure
            String departureDate = departureField.getDate();
            String returnDate = returnField.getDate();
            if (!departureDate.isEmpty() && !returnDate.isEmpty() && returnDate.compareTo(departureDate) < 0) {
                JOptionPane.showMessageDialog(this,
                    "Return date must be after departure date",
                    "Invalid Dates",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ensure a seat has been selected
            if (selectedSeatButton == null) {
                JOptionPane.showMessageDialog(this, "Please select a seat!");
                return;
            }

            // Get seat info from button name
            String[] seatInfo = selectedSeatButton.getName().split("-");
            String seatNumber = seatInfo[0];
            String seatClass = seatInfo[1];

            // Collect declarations
            StringBuilder declarations = new StringBuilder();
            for (JCheckBox checkbox : checkboxes) {
                if (checkbox.isSelected()) {
                    declarations.append(checkbox.getText()).append("\n");
                }
            }

            Passenger passenger = new Passenger(firstName + " " + lastName, "email@example.com", "555-0123");
            // Proceed with booking (availability check is simulated later for display)
            bookingService.bookFlight(selectedFlight, passenger, seatNumber, seatClass);
            Booking booking = bookingService.getBookings().get(bookingService.getBookings().size() - 1);
            
            WeatherInfo weather = weatherService.getWeather(selectedFlight.getDestination());
            
            // Confirmation dialog setup
            JDialog confirmationDialog = new JDialog(this, "Booking Confirmation", true);
            confirmationDialog.setLayout(new BorderLayout(0, 0));
            confirmationDialog.setSize(800, 700);
            confirmationDialog.setLocationRelativeTo(this);

            // Create main scroll pane
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(Color.WHITE);

            // Header Panel with gradient
            JPanel headerPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    int w = getWidth();
                    int h = getHeight();
                    GradientPaint gp = new GradientPaint(0, 0, primaryColor, w, h, new Color(144, 95, 192));
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, w, h);
                }
            };
            headerPanel.setPreferredSize(new Dimension(800, 100));
            headerPanel.setLayout(new BorderLayout());
            
            JLabel confirmLabel = new JLabel("Booking Confirmation", SwingConstants.CENTER);
            confirmLabel.setFont(new Font("Arial", Font.BOLD, 32));
            confirmLabel.setForeground(Color.WHITE);
            headerPanel.add(confirmLabel, BorderLayout.CENTER);

            // Content Panel
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

            // Passenger Info Section
            JPanel passengerPanel = createSectionPanel("Passenger Information", primaryColor);
            addDetailRow(passengerPanel, "Name:", firstName + " " + lastName);
            addDetailRow(passengerPanel, "Flight:", selectedFlight.getFlightNumber() + " to " + selectedFlight.getDestination());
            addDetailRow(passengerPanel, "Seat:", seatNumber + " (" + seatClass + ")");
            addDetailRow(passengerPanel, "Departure:", departureDate);
            addDetailRow(passengerPanel, "Return:", returnDate);
            contentPanel.add(passengerPanel);
            addVerticalSpace(contentPanel, 20);

            // Weather Section
            JPanel weatherPanel = createSectionPanel("Weather at " + selectedFlight.getDestination(), primaryColor);
            String[] weatherLines = weather.toString().split("\\n");
            for (String line : weatherLines) {
                if (!line.trim().isEmpty()) {
                    addDetailRow(weatherPanel, "", line);
                }
            }
            contentPanel.add(weatherPanel);
            addVerticalSpace(contentPanel, 20);

            // Packing List Section
            JPanel packingPanel = createSectionPanel("Recommended Packing List", primaryColor);
            for (String item : weather.getPackingList()) {
                addDetailRow(packingPanel, "•", item);
            }
            contentPanel.add(packingPanel);
            addVerticalSpace(contentPanel, 20);

            // Declarations Section (if any)
            if (declarations.length() > 0) {
                JPanel declarationsPanel = createSectionPanel("Declared Items", primaryColor);
                for (String line : declarations.toString().split("\\n")) {
                    if (!line.trim().isEmpty()) {
                        addDetailRow(declarationsPanel, "•", line);
                    }
                }
                contentPanel.add(declarationsPanel);
                addVerticalSpace(contentPanel, 20);
            }

            // Button Panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
            buttonPanel.setBackground(Color.WHITE);
            
            JButton printButton = new JButton("Print Itinerary");
            styleButton(printButton, primaryColor);
            printButton.addActionListener(evt -> {
                try {
                    String fileName = "itinerary_" + selectedFlight.getFlightNumber() + "_" + 
                                   firstName.toLowerCase() + "_" + lastName.toLowerCase() + ".txt";
                    
                    // Create the text content for the file
                    StringBuilder fileContent = new StringBuilder();
                    fileContent.append("BOOKING CONFIRMATION\n")
                             .append("===================\n\n")
                             .append("Passenger: ").append(firstName).append(" ").append(lastName).append("\n")
                             .append("Flight: ").append(selectedFlight.getFlightNumber())
                             .append(" to ").append(selectedFlight.getDestination()).append("\n")
                             .append("Seat: ").append(seatNumber).append(" (").append(seatClass).append(")\n")
                             .append("Departure: ").append(departureDate).append("\n")
                             .append("Return: ").append(returnDate).append("\n\n")
                             .append("Weather Information:\n")
                             .append(weather.toString()).append("\n\n")
                             .append("Recommended Packing List:\n");
                    
                    for (String item : weather.getPackingList()) {
                        fileContent.append("- ").append(item).append("\n");
                    }
                    
                    if (declarations.length() > 0) {
                        fileContent.append("\nDeclared Items:\n")
                                 .append(declarations.toString());
                    }
                    
                    java.nio.file.Files.write(
                        java.nio.file.Paths.get(fileName),
                        fileContent.toString().getBytes()
                    );
                    JOptionPane.showMessageDialog(confirmationDialog,
                        "Itinerary saved as: " + fileName,
                        "File Saved",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(confirmationDialog,
                        "Error saving itinerary: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            buttonPanel.add(printButton);
            
            JButton closeButton = new JButton("Close");
            styleButton(closeButton, Color.GRAY);
            closeButton.addActionListener(evt -> confirmationDialog.dispose());
            buttonPanel.add(closeButton);

            // Add all components to the dialog
            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);

            confirmationDialog.add(headerPanel, BorderLayout.NORTH);
            confirmationDialog.add(scrollPane, BorderLayout.CENTER);
            confirmationDialog.add(buttonPanel, BorderLayout.SOUTH);

            // Show dialog
            confirmationDialog.setVisible(true);
        });

        // Update seat rows based on class selection - REMOVED as seatRowCombo is unused
        // seatClassCombo.addActionListener(e -> {
        //     String selectedClass = (String) seatClassCombo.getSelectedItem();
        //     updateSeatRows(selectedClass);
        // });
    }

    private void updateFlightList() {
        flightListModel.clear();
        for (Flight flight : flightService.getAllFlights()) {
            flightListModel.addElement(flight);
        }
    }

    private void updateSeatDisplay() {
        Flight selectedFlight = flightList.getSelectedValue();
        if (selectedFlight == null) return;

        System.out.println("Updating seat display for flight: " + selectedFlight.getFlightNumber()); // Debug log

        // --- Simulate unavailable seats ---
        // NOTE: This section simulates seat availability for demonstration.
        // It uses a consistent random pattern based on the flight number,
        // rather than actual booking data. The Flight.isSeatAvailable() method
        // is currently NOT used for display purposes in this GUI version.
        Set<String> unavailableSeats = new HashSet<>();
        List<String> allSeatKeys = new ArrayList<>();
        int totalSeats = 0;

        // Define seat sections (mirroring createSeatGrid structure)
        String[] firstClassLetters = Flight.getSeatLetters("First Class");
        int firstClassRows = 8;
        totalSeats += firstClassRows * firstClassLetters.length;
        for (int r = 1; r <= firstClassRows; r++) {
            for (String l : firstClassLetters) allSeatKeys.add(r + l + "-First Class");
        }

        String[] economyLetters = Flight.getSeatLetters("Economy");
        int economyRows = 15 - 9 + 1;
        totalSeats += economyRows * economyLetters.length;
        for (int r = 9; r <= 15; r++) {
            for (String l : economyLetters) allSeatKeys.add(r + l + "-Economy");
        }

        String[] businessLetters = Flight.getSeatLetters("Business");
        int businessRows = 20 - 16 + 1;
        totalSeats += businessRows * businessLetters.length;
        for (int r = 16; r <= 20; r++) {
            for (String l : businessLetters) allSeatKeys.add(r + l + "-Business");
        }

        // Seed random based on flight number for consistency
        Random random = new Random(selectedFlight.getFlightNumber().hashCode());
        double percentageUnavailable = 0.20 + (0.50 - 0.20) * random.nextDouble();
        int numUnavailable = (int) (totalSeats * percentageUnavailable);

        // Shuffle and pick unavailable seats
        Collections.shuffle(allSeatKeys, random);
        for (int i = 0; i < numUnavailable && i < allSeatKeys.size(); i++) {
            unavailableSeats.add(allSeatKeys.get(i));
        }
        System.out.println("Marking " + numUnavailable + " seats as unavailable."); // Debug log
        // --- End simulation ---

        // Panel references are now instance variables

        // Remove old scroll pane if it exists
        if (seatGridPanel.getComponentCount() > 1) {
            seatGridPanel.remove(1);
        }

        // Create new seat grid with updated availability
        Color primaryColor = new Color(79, 40, 114);  // Deep purple
        Color accentColor = new Color(144, 95, 192);  // Light purple
        JScrollPane newSeatGrid = createSeatGrid(primaryColor, accentColor, unavailableSeats);
        seatGridPanel.add(newSeatGrid, BorderLayout.CENTER); // Add to center

        // Force immediate refresh
        seatGridPanel.revalidate();
        seatGridPanel.repaint();
        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        // Reset selected seat
        selectedSeatButton = null;
    }

    private JScrollPane createSeatGrid(Color primaryColor, Color accentColor) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // First Class Section (Rows 1-8)
        addSectionLabel(mainPanel, "First Class", gbc, 0, primaryColor);
        createSeatSection(mainPanel, 1, 8, Flight.getSeatLetters("First Class"), "First Class", gbc, 1, true, primaryColor, accentColor);

        // Economy Section (Rows 9-15)
        addSectionLabel(mainPanel, "Economy", gbc, 10, primaryColor);
        createSeatSection(mainPanel, 9, 15, Flight.getSeatLetters("Economy"), "Economy", gbc, 11, false, primaryColor, accentColor);

        // Business Section (Rows 16-20)
        addSectionLabel(mainPanel, "Business", gbc, 20, primaryColor);
        createSeatSection(mainPanel, 16, 20, Flight.getSeatLetters("Business"), "Business", gbc, 21, false, primaryColor, accentColor);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(435, 650));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }

    private void addSectionLabel(JPanel panel, String text, GridBagConstraints gbc, int gridy, Color primaryColor) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(primaryColor);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(20, 10, 15, 10);  // Adjusted bottom padding
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);
        
        // Reset constraints
        gbc.gridwidth = 1;
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.CENTER;
    }

    private void createSeatSection(JPanel panel, int startRow, int endRow, String[] letters, 
                                 String seatClass, GridBagConstraints gbc, int startGridY, 
                                 boolean isFirstClass, Color primaryColor, Color accentColor) {
        // Add empty space for centering
        int emptySpacesStart = isFirstClass ? 1 : 0;
        
        // Add column headers (letters)
        gbc.gridy = startGridY;
        gbc.gridx = emptySpacesStart;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        for (int i = 0; i < letters.length; i++) {
            if (i == letters.length / 2) {
                JLabel spacerLabel = new JLabel(" ");
                spacerLabel.setPreferredSize(new Dimension(30, 20));
                panel.add(spacerLabel, gbc);
                gbc.gridx++;
            }
            JLabel letterLabel = new JLabel(letters[i]);
            letterLabel.setFont(new Font("Arial", Font.BOLD, 12));
            letterLabel.setForeground(primaryColor);
            letterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            letterLabel.setPreferredSize(new Dimension(44, 20));
            panel.add(letterLabel, gbc);
            gbc.gridx++;
        }
        
        // Add rows
        for (int row = startRow; row <= endRow; row++) {
            gbc.gridy++;
            gbc.gridx = emptySpacesStart;
            
            for (int i = 0; i < letters.length; i++) {
                if (i == letters.length / 2) {
                    JLabel spacerLabel = new JLabel(" ");
                    spacerLabel.setPreferredSize(new Dimension(30, 44));
                    panel.add(spacerLabel, gbc);
                    gbc.gridx++;
                }
                
                SeatButton seatButton = new SeatButton();
                seatButton.setPreferredSize(new Dimension(44, 44));
                seatButton.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
                seatButton.setFocusPainted(false);
                String seatNumber = row + letters[i];
                String seatKey = seatNumber + "-" + seatClass;
                seatButton.setName(seatKey);
                seatButton.setText(seatNumber);
                seatButton.setFont(new Font("Arial", Font.PLAIN, 11));
                seatButton.setMargin(new Insets(0, 0, 0, 0));
                seatButton.addActionListener(e -> handleSeatSelection((JButton)e.getSource()));
                panel.add(seatButton, gbc);
                gbc.gridx++;
            }
        }
    }

    private void handleSeatSelection(JButton button) {
        if (!(button instanceof SeatButton)) return;
        SeatButton seatButton = (SeatButton) button;
        
        // Clear previous selection if it exists
        if (selectedSeatButton != null && selectedSeatButton instanceof SeatButton) {
            ((SeatButton) selectedSeatButton).setSelected(false);
        }
        
        // Update new selection
        selectedSeatButton = seatButton;
        seatButton.setSelected(true);
        
        // Parse seat information
        String[] parts = seatButton.getName().split("-");
        String seatClass = parts[1];
        
        // Update class selection in combo box if needed
        if (seatClassCombo != null && !seatClassCombo.getSelectedItem().equals(seatClass)) {
            seatClassCombo.setSelectedItem(seatClass);
        }
    }

    // Helper methods for confirmation dialog
    private JPanel createSectionPanel(String title, Color primaryColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(primaryColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        addVerticalSpace(panel, 10);

        return panel;
    }

    private void addDetailRow(JPanel panel, String label, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        row.setBackground(Color.WHITE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (!label.isEmpty()) {
            JLabel labelComponent = new JLabel(label);
            labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
            labelComponent.setPreferredSize(new Dimension(100, 20));
            row.add(labelComponent);
        }

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 14));
        row.add(valueComponent);

        panel.add(row);
    }

    private void addVerticalSpace(JPanel panel, int height) {
        panel.add(Box.createRigidArea(new Dimension(0, height)));
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
    }

    private JScrollPane createSeatGrid(Color primaryColor, Color accentColor, Set<String> unavailableSeats) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // First Class Section (Rows 1-8)
        addSectionLabel(mainPanel, "First Class", gbc, 0, primaryColor);
        createSeatSection(mainPanel, 1, 8, Flight.getSeatLetters("First Class"), "First Class", gbc, 1, true, primaryColor, accentColor, unavailableSeats);

        // Economy Section (Rows 9-15)
        addSectionLabel(mainPanel, "Economy", gbc, 10, primaryColor);
        createSeatSection(mainPanel, 9, 15, Flight.getSeatLetters("Economy"), "Economy", gbc, 11, false, primaryColor, accentColor, unavailableSeats);

        // Business Section (Rows 16-20)
        addSectionLabel(mainPanel, "Business", gbc, 20, primaryColor);
        createSeatSection(mainPanel, 16, 20, Flight.getSeatLetters("Business"), "Business", gbc, 21, false, primaryColor, accentColor, unavailableSeats);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(435, 650));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }

    private void createSeatSection(JPanel panel, int startRow, int endRow, String[] letters,
                                 String seatClass, GridBagConstraints gbc, int startGridY,
                                 boolean isFirstClass, Color primaryColor, Color accentColor, Set<String> unavailableSeats) {
        // Add empty space for centering
        int emptySpacesStart = isFirstClass ? 1 : 0;

        // Add column headers (letters)
        gbc.gridy = startGridY;
        gbc.gridx = emptySpacesStart;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < letters.length; i++) {
            if (i == letters.length / 2) {
                JLabel spacerLabel = new JLabel(" ");
                spacerLabel.setPreferredSize(new Dimension(30, 20));
                panel.add(spacerLabel, gbc);
                gbc.gridx++;
            }
            JLabel letterLabel = new JLabel(letters[i]);
            letterLabel.setFont(new Font("Arial", Font.BOLD, 12));
            letterLabel.setForeground(primaryColor);
            letterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            letterLabel.setPreferredSize(new Dimension(44, 20));
            panel.add(letterLabel, gbc);
            gbc.gridx++;
        }

        // Add rows
        for (int row = startRow; row <= endRow; row++) {
            gbc.gridy++;
            gbc.gridx = emptySpacesStart;

            for (int i = 0; i < letters.length; i++) {
                if (i == letters.length / 2) {
                    JLabel spacerLabel = new JLabel(" ");
                    spacerLabel.setPreferredSize(new Dimension(30, 44));
                    panel.add(spacerLabel, gbc);
                    gbc.gridx++;
                }

                SeatButton seatButton = new SeatButton();
                seatButton.setPreferredSize(new Dimension(44, 44));
                seatButton.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
                seatButton.setFocusPainted(false);
                String seatNumber = row + letters[i];
                String seatKey = seatNumber + "-" + seatClass;
                seatButton.setName(seatKey);
                seatButton.setText(seatNumber);
                seatButton.setFont(new Font("Arial", Font.PLAIN, 11));
                seatButton.setMargin(new Insets(0, 0, 0, 0));

                // Check if seat is unavailable based on simulation
                if (unavailableSeats.contains(seatKey)) {
                    seatButton.setBackground(new Color(200, 200, 200)); // Grey
                    seatButton.setEnabled(false);
                    // Remove hover effect for unavailable seats
                    for (MouseListener ml : seatButton.getMouseListeners()) {
                        if (ml instanceof MouseAdapter) {
                             // Removing the adapter is simplest
                             seatButton.removeMouseListener(ml);
                        }
                    }
                } else {
                    // Only add listener for available seats
                    seatButton.addActionListener(e -> handleSeatSelection((JButton)e.getSource()));
                }

                panel.add(seatButton, gbc);
                gbc.gridx++;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FlightManagementGUI().setVisible(true);
        });
    }
}
