package FlightManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class BookingConfirmationGUI extends JFrame {
    private Booking booking;
    private WeatherInfo weatherInfo;

    public BookingConfirmationGUI(Booking booking, WeatherInfo weatherInfo) {
        this.booking = booking;
        this.weatherInfo = weatherInfo;

        // Set dark theme colors
        Color darkBackground = new Color(33, 33, 33);
        Color darkerBackground = new Color(25, 25, 25);
        Color accentColor = new Color(0, 150, 255);
        Color textColor = new Color(230, 230, 230);

        setTitle("Booking Confirmation");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(darkBackground);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(accentColor);
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("Booking Confirmation");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(darkBackground);

        // Information Panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(darkBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Passenger Information
        addSectionHeader(infoPanel, "Passenger Information", gbc, 0, textColor);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        addLabel(infoPanel, "Name:", gbc, textColor);
        gbc.gridx = 1;
        addLabel(infoPanel, booking.getPassenger().getName(), gbc, textColor);

        // Flight Information
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        addSectionHeader(infoPanel, "Flight Information", gbc, 2, textColor);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 1;
        addLabel(infoPanel, "Destination:", gbc, textColor);
        gbc.gridx = 1;
        addLabel(infoPanel, booking.getFlight().getDestination(), gbc, textColor);

        gbc.gridx = 0; gbc.gridy = 4;
        addLabel(infoPanel, "Class:", gbc, textColor);
        gbc.gridx = 1;
        addLabel(infoPanel, booking.getSeatClass(), gbc, textColor);

        gbc.gridx = 0; gbc.gridy = 5;
        addLabel(infoPanel, "Seat:", gbc, textColor);
        gbc.gridx = 1;
        addLabel(infoPanel, booking.getSeatNumber(), gbc, textColor);

        // Weather Information
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        addSectionHeader(infoPanel, "Weather Information", gbc, 6, textColor);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 1;
        addLabel(infoPanel, "Temperature:", gbc, textColor);
        gbc.gridx = 1;
        addLabel(infoPanel, String.format("%.1f°C", weatherInfo.getTemperature()), gbc, textColor);

        gbc.gridx = 0; gbc.gridy = 8;
        addLabel(infoPanel, "Conditions:", gbc, textColor);
        gbc.gridx = 1;
        addLabel(infoPanel, weatherInfo.getCondition(), gbc, textColor);

        // Packing List
        gbc.gridx = 0; gbc.gridy = 9;
        gbc.gridwidth = 2;
        addSectionHeader(infoPanel, "Recommended Packing List", gbc, 9, textColor);

        JTextArea packingList = new JTextArea();
        packingList.setEditable(false);
        packingList.setBackground(darkerBackground);
        packingList.setForeground(textColor);
        packingList.setFont(new Font("Arial", Font.PLAIN, 16));
        packingList.setLineWrap(true);
        packingList.setWrapStyleWord(true);
        packingList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (String item : weatherInfo.getPackingList()) {
            packingList.append("• " + item + "\n");
        }
        
        JScrollPane packingScrollPane = new JScrollPane(packingList);
        packingScrollPane.setPreferredSize(new Dimension(600, 300));
        packingScrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        packingScrollPane.getViewport().setBackground(darkerBackground);
        
        gbc.gridx = 0; gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 5, 20, 5);
        infoPanel.add(packingScrollPane, gbc);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(darkBackground);
        
        JButton downloadButton = new JButton("Download Itinerary");
        downloadButton.setFont(new Font("Arial", Font.BOLD, 16));
        downloadButton.setBackground(accentColor);
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setFocusPainted(false);
        downloadButton.setBorder(BorderFactory.createRaisedBevelBorder());
        downloadButton.setPreferredSize(new Dimension(250, 50));
        
        downloadButton.addActionListener(e -> {
            String fileName = String.format("itinerary_%s_%s.txt",
                booking.getPassenger().getName().replaceAll("\\s+", "_"),
                booking.getFlight().getFlightNumber());
            
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(booking.generateItinerary(weatherInfo));
                JOptionPane.showMessageDialog(this, 
                    "Itinerary saved to " + fileName,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error saving itinerary: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(downloadButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void addSectionHeader(JPanel panel, String text, GridBagConstraints gbc, int gridy, Color textColor) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(textColor);
        panel.add(label, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
    }

    private void addLabel(JPanel panel, String text, GridBagConstraints gbc, Color textColor) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(textColor);
        panel.add(label, gbc);
    }
} 