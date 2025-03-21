import java.util.ArrayList;
import java.util.List;

public class PackingList {

    private Flight flight;
    private List<String> weatherConditions;
    private int vacationLength;
    private List<String> recommendedItems;

    public PackingList(Flight flight, List<String> weatherConditions, int vacationLength) {
        this.flight = flight;
        this.weatherConditions = weatherConditions;
        this.vacationLength = vacationLength;
        this.recommendedItems = new ArrayList<>();
    }

    public void generatePackingList() {
        recommendedItems.add("Passport");
        recommendedItems.add("Phone Charger");
        recommendedItems.add("Toiletries");
        recommendedItems.add("Medications");
        recommendedItems.add("Travel Documents");
        recommendedItems.add("Credit Cards and Cash");
        recommendedItems.add("Travel Insurance Documents");
        recommendedItems.add("Emergency Contacts List");

        recommendedItems.add("Underwear");
        recommendedItems.add("Socks");
        recommendedItems.add("Shirts/T-shirts");
        recommendedItems.add("Pants/Shorts");
        recommendedItems.add("Sleepwear");
        recommendedItems.add("Comfortable Shoes");

        for (String condition : weatherConditions) {
            switch (condition.toLowerCase()) {
                case "cold":
                    recommendedItems.add("Warm Jacket");
                    recommendedItems.add("Sweaters");
                    recommendedItems.add("Thermal Underwear");
                    recommendedItems.add("Gloves");
                    recommendedItems.add("Scarf");
                    recommendedItems.add("Winter Hat");
                    recommendedItems.add("Warm Socks");
                    recommendedItems.add("Boots");
                    break;

                case "hot":
                    recommendedItems.add("Sunscreen");
                    recommendedItems.add("Sunglasses");
                    recommendedItems.add("Sunhat");
                    recommendedItems.add("Light Clothing");
                    recommendedItems.add("Sandals");
                    recommendedItems.add("Swimsuit");
                    recommendedItems.add("Beach Towel");
                    break;

                case "rainy":
                    recommendedItems.add("Umbrella");
                    recommendedItems.add("Waterproof Jacket");
                    recommendedItems.add("Waterproof Shoes");
                    recommendedItems.add("Quick-Dry Clothes");
                    break;

                case "snowy":
                    recommendedItems.add("Winter Boots");
                    recommendedItems.add("Snow Pants");
                    recommendedItems.add("Thermal Layers");
                    recommendedItems.add("Snow Gloves");
                    break;

                case "windy":
                    recommendedItems.add("Windbreaker");
                    recommendedItems.add("Scarf");
                    break;
            }
        }

        if (vacationLength > 7) {
            recommendedItems.add("Extra Clothes");
            recommendedItems.add("Laundry Supplies");
            recommendedItems.add("Entertainment (books, games)");
        }

        recommendedItems.add("Camera");
        recommendedItems.add("Travel Pillow");
        recommendedItems.add("Reusable Water Bottle");
        recommendedItems.add("Snacks");
        recommendedItems.add("Headphones");
        recommendedItems.add("Backpack");
    }

    public void displayPackingList() {
        System.out.println("Packing List for " + flight.getDestination() + ":");
        for (String item : recommendedItems) {
            System.out.println("- " + item);
        }
    }

    public List<String> getRecommendedItems() {
        return recommendedItems;
    }
//  Have to auto assign weather based on openweather API, but for now manually adding weather works
    public static void main(String[] args) {
        Flight flight = new Flight("F101", "Fredericton", "London", 5);
        List<String> weather = new ArrayList<>();
        weather.add("Cold");
        weather.add("Rainy");

        PackingList trip = new PackingList(flight, weather, 10);
        trip.generatePackingList();
        trip.displayPackingList();
    }
}
