import java.util.Scanner;
 
public class BudgetPlanner {
    private double totalBudget;
    private double flightCost;
    private double hotelCost;
    private double foodCost;
    private double transportCost;
    private double activitiesCost;
 
    public BudgetPlanner(double totalBudget) {
        this.totalBudget = totalBudget;
    }
 
    public void setExpenses(double flight, double hotel, double food, double transport, double activities) {
        this.flightCost = flight;
        this.hotelCost = hotel;
        this.foodCost = food;
        this.transportCost = transport;
        this.activitiesCost = activities;
    }
 
    public double calculateTotalSpent() {
        return flightCost + hotelCost + foodCost + transportCost + activitiesCost;
    }
 
    public double calculateRemainingBudget() {
        return totalBudget - calculateTotalSpent();
    }
 
    public void displaySummary() {
        System.out.println("\n--- Budget Summary ---");
        System.out.println("Total Budget: $" + totalBudget);
        System.out.println("Total Spent: $" + calculateTotalSpent());
        System.out.println("Remaining Budget: $" + calculateRemainingBudget());
 
        if (calculateRemainingBudget() < 0) {
            System.out.println("Warning: You have exceeded your budget!");
        }
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your total vacation budget: ");
        double totalBudget = scanner.nextDouble();
        BudgetPlanner planner = new BudgetPlanner(totalBudget);
        planner.setExpenses(
            getExpense(scanner, "Flight"),
            getExpense(scanner, "Hotel"),
            getExpense(scanner, "Food"),
            getExpense(scanner, "Transport"),
            getExpense(scanner, "Activities")
        );
        planner.displaySummary();
        scanner.close();
    }
    private static double getExpense(Scanner scanner, String category) {
        System.out.print("Enter estimated cost for " + category + ": ");
        return scanner.nextDouble();
    }
}
