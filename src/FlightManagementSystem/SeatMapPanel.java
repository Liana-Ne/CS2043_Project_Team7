package FlightManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SeatMapPanel extends JPanel {
    private static final int SEAT_SIZE = 50;
    private static final int SPACING = 5;
    private static final int AISLE_WIDTH = 40;
    
    // Class-specific constants
    private static final int FIRST_CLASS_ROWS = 8;
    private static final int BUSINESS_CLASS_ROWS = 10;  // Rows 9-18
    private static final int ECONOMY_CLASS_ROWS = 12;   // Rows 19-30
    private static final int FIRST_CLASS_COLS = 4;      // A-D
    private static final int REGULAR_COLS = 6;          // A-F
    private static final int FIRST_CLASS_AISLE_AFTER_COL = 2; // Aisle after B for First Class
    private static final int REGULAR_AISLE_AFTER_COL = 3;     // Aisle after C for Regular
    
    private String selectedSeat = null;
    private Set<String> availableSeats = new HashSet<>();
    private Set<String> bookedSeats = new HashSet<>();
    
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color SEAT_COLOR = new Color(240, 240, 240);
    private static final Color SELECTED_COLOR = new Color(66, 134, 244);
    private static final Color BOOKED_COLOR = new Color(200, 200, 200);
    private static final Color BORDER_COLOR = new Color(180, 180, 180);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color SECTION_TITLE_COLOR = new Color(100, 100, 100);
    
    // Re-add this field if needed by setCurrentClass
    private String currentClass;
    
    public SeatMapPanel() {
        setBackground(BACKGROUND_COLOR);
        initializeSeats();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleSeatClick(e.getX(), e.getY());
            }
        });
        
        // Set preferred size based on layout
        int firstClassWidth = FIRST_CLASS_COLS * (SEAT_SIZE + SPACING) + AISLE_WIDTH + SPACING;
        int regularWidth = REGULAR_COLS * (SEAT_SIZE + SPACING) + AISLE_WIDTH + SPACING;
        int width = Math.max(firstClassWidth, regularWidth);
        int height = (FIRST_CLASS_ROWS + BUSINESS_CLASS_ROWS + ECONOMY_CLASS_ROWS) *
                    (SEAT_SIZE + SPACING) + 4 * SPACING + 60; // Extra space for section titles
        
        // Set both minimum and preferred size
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
    }
    
    private void initializeSeats() {
        // Initialize First Class seats (1A-8D) - Updated rows
        for (int row = 1; row <= FIRST_CLASS_ROWS; row++) {
            for (char col = 'A'; col < 'A' + FIRST_CLASS_COLS; col++) { // Loop to D
                availableSeats.add(row + String.valueOf(col));
            }
        }
        
        // Initialize Business Class seats (9A-18F) - Updated starting row
        int businessStartRow = FIRST_CLASS_ROWS + 1;
        for (int row = businessStartRow; row < businessStartRow + BUSINESS_CLASS_ROWS; row++) {
            for (char col = 'A'; col < 'A' + REGULAR_COLS; col++) {
                availableSeats.add(row + String.valueOf(col));
            }
        }
        
        // Initialize Economy Class seats (19A-30F) - Updated starting row
        int economyStartRow = businessStartRow + BUSINESS_CLASS_ROWS;
        for (int row = economyStartRow; row < economyStartRow + ECONOMY_CLASS_ROWS; row++) {
            for (char col = 'A'; col < 'A' + REGULAR_COLS; col++) {
                availableSeats.add(row + String.valueOf(col));
            }
        }
        
        // Randomly book some seats
        Random random = new Random();
        for (String seat : availableSeats.toArray(new String[0])) {
            if (random.nextDouble() < 0.3) { // 30% chance of being booked
                bookedSeats.add(seat);
            }
        }
    }
    
    private void handleSeatClick(int x, int y) {
        String seat = getSeatFromCoordinates(x, y);
        if (seat != null && !bookedSeats.contains(seat)) {
            if (seat.equals(selectedSeat)) {
                selectedSeat = null;
            } else {
                selectedSeat = seat;
            }
            repaint();
        }
    }
    
    private String getSeatFromCoordinates(int x, int y) {
        int verticalOffset = SPACING + 20; // Start below "First Class" title
        int panelWidth = getWidth(); // Get the actual panel width

        // --- First Class section (Rows 1-8, Cols A-D) ---
        int firstClassSectionWidth = FIRST_CLASS_COLS * (SEAT_SIZE + SPACING) + AISLE_WIDTH; // Width of seats+aisle
        int firstClassStartX = (panelWidth - firstClassSectionWidth) / 2; // Centering offset
        int firstClassEndRow = FIRST_CLASS_ROWS;
        int firstClassSectionHeight = FIRST_CLASS_ROWS * (SEAT_SIZE + SPACING);

        if (y >= verticalOffset && y < verticalOffset + firstClassSectionHeight) {
            int row = (y - verticalOffset) / (SEAT_SIZE + SPACING) + 1;
            if (row >= 1 && row <= firstClassEndRow) {
                // Aisle logic for First Class, adjusted for startX
                int aisleXStartRelative = FIRST_CLASS_AISLE_AFTER_COL * (SEAT_SIZE + SPACING);
                int aisleXStartAbsolute = firstClassStartX + aisleXStartRelative;
                int aisleXEndAbsolute = aisleXStartAbsolute + AISLE_WIDTH;

                int colIndex = -1;
                int seatX = -1;

                if (x >= firstClassStartX && x < aisleXStartAbsolute) { // Click is before the aisle
                    colIndex = (x - firstClassStartX) / (SEAT_SIZE + SPACING);
                    if (colIndex >= 0 && colIndex < FIRST_CLASS_AISLE_AFTER_COL) {
                         seatX = firstClassStartX + colIndex * (SEAT_SIZE + SPACING);
                    } else {
                        colIndex = -1;
                    }
                } else if (x >= aisleXEndAbsolute && x < firstClassStartX + firstClassSectionWidth) { // Click is after the aisle
                    colIndex = FIRST_CLASS_AISLE_AFTER_COL + (x - aisleXEndAbsolute) / (SEAT_SIZE + SPACING);
                     if (colIndex >= FIRST_CLASS_AISLE_AFTER_COL && colIndex < FIRST_CLASS_COLS) {
                         seatX = aisleXEndAbsolute + (colIndex - FIRST_CLASS_AISLE_AFTER_COL) * (SEAT_SIZE + SPACING);
                     } else {
                         colIndex = -1;
                     }
                }

                if (colIndex != -1 && seatX != -1 && x >= seatX && x < seatX + SEAT_SIZE) {
                    return row + String.valueOf((char)('A' + colIndex));
                }
            }
        }
        verticalOffset += firstClassSectionHeight + 20 + 20; // Add space below section + title height
        
        // --- Business Class section (Rows 9-18) ---
        int regularSectionWidth = REGULAR_COLS * (SEAT_SIZE + SPACING) + AISLE_WIDTH; // Width of seats+aisle
        int regularStartX = (panelWidth - regularSectionWidth) / 2; // Centering offset
        int businessStartRow = FIRST_CLASS_ROWS + 1;
        int businessEndRow = businessStartRow + BUSINESS_CLASS_ROWS - 1;
        int businessSectionHeight = BUSINESS_CLASS_ROWS * (SEAT_SIZE + SPACING);

        if (y >= verticalOffset && y < verticalOffset + businessSectionHeight) {
            int row = (y - verticalOffset) / (SEAT_SIZE + SPACING) + businessStartRow;
            if (row >= businessStartRow && row <= businessEndRow) {
                // Aisle logic for Regular Class, adjusted for startX
                int aisleXStartRelative = REGULAR_AISLE_AFTER_COL * (SEAT_SIZE + SPACING);
                int aisleXStartAbsolute = regularStartX + aisleXStartRelative;
                int aisleXEndAbsolute = aisleXStartAbsolute + AISLE_WIDTH;

                int colIndex = -1;
                int seatX = -1;

                if (x >= regularStartX && x < aisleXStartAbsolute) { // Before aisle
                    colIndex = (x - regularStartX) / (SEAT_SIZE + SPACING);
                    if (colIndex >= 0 && colIndex < REGULAR_AISLE_AFTER_COL) {
                         seatX = regularStartX + colIndex * (SEAT_SIZE + SPACING);
                    } else { colIndex = -1; }
                } else if (x >= aisleXEndAbsolute && x < regularStartX + regularSectionWidth) { // After aisle
                    colIndex = REGULAR_AISLE_AFTER_COL + (x - aisleXEndAbsolute) / (SEAT_SIZE + SPACING);
                     if (colIndex >= REGULAR_AISLE_AFTER_COL && colIndex < REGULAR_COLS) {
                         seatX = aisleXEndAbsolute + (colIndex - REGULAR_AISLE_AFTER_COL) * (SEAT_SIZE + SPACING);
                     } else { colIndex = -1; }
                }

                if (colIndex != -1 && seatX != -1 && x >= seatX && x < seatX + SEAT_SIZE) {
                    return row + String.valueOf((char)('A' + colIndex));
                }
            }
        }
        verticalOffset += businessSectionHeight + 20 + 20; // Add space below section + title height
        
        // --- Economy Class section (Rows 19-30) ---
        // Uses the same regularStartX and regularSectionWidth as Business
        int economyStartRow = businessEndRow + 1;
        int economyEndRow = economyStartRow + ECONOMY_CLASS_ROWS - 1;
        int economySectionHeight = ECONOMY_CLASS_ROWS * (SEAT_SIZE + SPACING);

         if (y >= verticalOffset && y < verticalOffset + economySectionHeight) {
            int row = (y - verticalOffset) / (SEAT_SIZE + SPACING) + economyStartRow;
            if (row >= economyStartRow && row <= economyEndRow) {
                 // Aisle logic for Regular Class, adjusted for startX
                 int aisleXStartRelative = REGULAR_AISLE_AFTER_COL * (SEAT_SIZE + SPACING);
                 int aisleXStartAbsolute = regularStartX + aisleXStartRelative;
                 int aisleXEndAbsolute = aisleXStartAbsolute + AISLE_WIDTH;

                 int colIndex = -1;
                 int seatX = -1;

                 if (x >= regularStartX && x < aisleXStartAbsolute) { // Before aisle
                     colIndex = (x - regularStartX) / (SEAT_SIZE + SPACING);
                     if (colIndex >= 0 && colIndex < REGULAR_AISLE_AFTER_COL) {
                          seatX = regularStartX + colIndex * (SEAT_SIZE + SPACING);
                     } else { colIndex = -1; }
                 } else if (x >= aisleXEndAbsolute && x < regularStartX + regularSectionWidth) { // After aisle
                     colIndex = REGULAR_AISLE_AFTER_COL + (x - aisleXEndAbsolute) / (SEAT_SIZE + SPACING);
                      if (colIndex >= REGULAR_AISLE_AFTER_COL && colIndex < REGULAR_COLS) {
                          seatX = aisleXEndAbsolute + (colIndex - REGULAR_AISLE_AFTER_COL) * (SEAT_SIZE + SPACING);
                      } else { colIndex = -1; }
                 }

                 if (colIndex != -1 && seatX != -1 && x >= seatX && x < seatX + SEAT_SIZE) {
                     return row + String.valueOf((char)('A' + colIndex));
                 }
            }
        }

        return null; // Click was outside any valid seat area
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int verticalOffset = SPACING;
        int panelWidth = getWidth(); // Get the actual panel width
        FontMetrics fm = g2d.getFontMetrics(new Font("Arial", Font.BOLD, 14)); // For centering text
        
        // Draw First Class section (Rows 1-8, Cols A-D)
        int firstClassSectionWidth = FIRST_CLASS_COLS * (SEAT_SIZE + SPACING) + AISLE_WIDTH; // Width of seats+aisle
        int firstClassStartX = (panelWidth - firstClassSectionWidth) / 2; // Centering offset
        g2d.setColor(SECTION_TITLE_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        String firstTitle = "First Class";
        int firstTitleWidth = fm.stringWidth(firstTitle);
        g2d.drawString(firstTitle, (panelWidth - firstTitleWidth) / 2, verticalOffset + 15); // Centered title
        verticalOffset += 20;
        
        for (int row = 1; row <= FIRST_CLASS_ROWS; row++) {
            for (int col = 0; col < FIRST_CLASS_COLS; col++) {
                String seat = row + String.valueOf((char)('A' + col));
                // Calculate seatX relative to firstClassStartX
                int seatX;
                if (col < FIRST_CLASS_AISLE_AFTER_COL) {
                    seatX = firstClassStartX + col * (SEAT_SIZE + SPACING);
                } else {
                    seatX = firstClassStartX + col * (SEAT_SIZE + SPACING) + AISLE_WIDTH;
                }
                int seatY = verticalOffset + (row - 1) * (SEAT_SIZE + SPACING);
                drawSeat(g2d, seatX, seatY, seat);
            }
        }
        verticalOffset += FIRST_CLASS_ROWS * (SEAT_SIZE + SPACING) + 20; // Space after section
        
        // Draw Business Class section (Rows 9-18)
        int regularSectionWidth = REGULAR_COLS * (SEAT_SIZE + SPACING) + AISLE_WIDTH; // Width of seats+aisle
        int regularStartX = (panelWidth - regularSectionWidth) / 2; // Centering offset
        g2d.setColor(SECTION_TITLE_COLOR);
        String businessTitle = "Business Class";
        int businessTitleWidth = fm.stringWidth(businessTitle);
        g2d.drawString(businessTitle, (panelWidth - businessTitleWidth) / 2, verticalOffset + 15); // Centered title
        verticalOffset += 20;
        int businessStartRow = FIRST_CLASS_ROWS + 1;
        
        for (int row = businessStartRow; row < businessStartRow + BUSINESS_CLASS_ROWS; row++) {
            for (int col = 0; col < REGULAR_COLS; col++) {
                String seat = row + String.valueOf((char)('A' + col));
                // Calculate seatX relative to regularStartX
                int seatX;
                if (col < REGULAR_AISLE_AFTER_COL) {
                    seatX = regularStartX + col * (SEAT_SIZE + SPACING);
                } else {
                    seatX = regularStartX + col * (SEAT_SIZE + SPACING) + AISLE_WIDTH;
                }
                int seatY = verticalOffset + (row - businessStartRow) * (SEAT_SIZE + SPACING);
                drawSeat(g2d, seatX, seatY, seat);
            }
        }
        verticalOffset += BUSINESS_CLASS_ROWS * (SEAT_SIZE + SPACING) + 20; // Space after section
        
        // Draw Economy Class section (Rows 19-30)
        g2d.setColor(SECTION_TITLE_COLOR);
        String economyTitle = "Economy Class";
        int economyTitleWidth = fm.stringWidth(economyTitle);
        g2d.drawString(economyTitle, (panelWidth - economyTitleWidth) / 2, verticalOffset + 15); // Centered title
        verticalOffset += 20;
        int economyStartRow = businessStartRow + BUSINESS_CLASS_ROWS;
        
        for (int row = economyStartRow; row < economyStartRow + ECONOMY_CLASS_ROWS; row++) {
            for (int col = 0; col < REGULAR_COLS; col++) {
                String seat = row + String.valueOf((char)('A' + col));
                 // Calculate seatX relative to regularStartX
                 int seatX;
                 if (col < REGULAR_AISLE_AFTER_COL) {
                     seatX = regularStartX + col * (SEAT_SIZE + SPACING);
                 } else {
                     seatX = regularStartX + col * (SEAT_SIZE + SPACING) + AISLE_WIDTH;
                 }
                 int seatY = verticalOffset + (row - economyStartRow) * (SEAT_SIZE + SPACING);
                drawSeat(g2d, seatX, seatY, seat);
            }
        }
    }
    
    private void drawSeat(Graphics2D g2d, int x, int y, String seat) {
        // Draw seat background
        if (seat.equals(selectedSeat)) {
            g2d.setColor(SELECTED_COLOR);
        } else if (bookedSeats.contains(seat)) {
            g2d.setColor(BOOKED_COLOR);
        } else {
            g2d.setColor(SEAT_COLOR);
        }
        
        g2d.fillRoundRect(x, y, SEAT_SIZE, SEAT_SIZE, 10, 10);
        g2d.setColor(BORDER_COLOR);
        g2d.drawRoundRect(x, y, SEAT_SIZE, SEAT_SIZE, 10, 10);
        
        // Draw seat label
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (SEAT_SIZE - fm.stringWidth(seat)) / 2;
        int textY = y + ((SEAT_SIZE + fm.getAscent()) / 2);
        g2d.drawString(seat, textX, textY);
    }
    
    public String getSelectedSeat() {
        return selectedSeat;
    }
    
    public void clearSelection() {
        selectedSeat = null;
        repaint();
    }

    // --- Re-add setCurrentClass ---
    public void setCurrentClass(String selectedClass) {
        this.currentClass = selectedClass;
        // Add any logic that was originally here, or just repaint
        System.out.println("SeatMapPanel: Current class set to " + this.currentClass); // Optional debug
        repaint();
    }
    // --- End Re-add ---

    // --- Remove markSeatAsBooked ---
    // public void markSeatAsBooked(String seat) { ... }

    // --- Remove getClassForSeat ---
    // public String getClassForSeat(String seat) { ... }
} 