import FlightManagementSystem.FlightManagementGUI;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            FlightManagementGUI gui = new FlightManagementGUI();
            gui.setVisible(true);
        });
    }
}
