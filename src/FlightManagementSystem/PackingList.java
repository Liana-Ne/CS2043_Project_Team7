package FlightManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class PackingList {
    private List<String> items;
    
    public PackingList() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(String item) {
        if (item != null && !item.trim().isEmpty()) {
            items.add(item.trim());
        }
    }
    
    public void removeItem(String item) {
        items.remove(item);
    }
    
    public List<String> getItems() {
        return new ArrayList<>(items);
    }
    
    public static PackingList generateFromWeather(WeatherInfo weatherInfo) {
        PackingList packingList = new PackingList();
        double temperature = weatherInfo.getTemperature();
        String condition = weatherInfo.getCondition().toLowerCase();
        
        // Essential items
        packingList.addItem("Passport");
        packingList.addItem("Travel Documents");
        packingList.addItem("Phone and Charger");
        packingList.addItem("Wallet");
        
        // Temperature based items
        if (temperature < 10) {
            packingList.addItem("Winter Coat");
            packingList.addItem("Thermal Underwear");
            packingList.addItem("Gloves");
            packingList.addItem("Winter Hat");
            packingList.addItem("Scarf");
            packingList.addItem("Warm Socks");
            packingList.addItem("Boots");
        } else if (temperature < 20) {
            packingList.addItem("Light Jacket");
            packingList.addItem("Long Sleeve Shirts");
            packingList.addItem("Jeans");
            packingList.addItem("Closed-toe Shoes");
            packingList.addItem("Light Sweater");
        } else {
            packingList.addItem("T-Shirts");
            packingList.addItem("Shorts");
            packingList.addItem("Sandals");
            packingList.addItem("Sunglasses");
            packingList.addItem("Sunscreen");
            packingList.addItem("Hat");
        }
        
        // Weather condition based items
        if (condition.contains("rain")) {
            packingList.addItem("Rain Jacket");
            packingList.addItem("Umbrella");
            packingList.addItem("Waterproof Shoes");
        } else if (condition.contains("snow")) {
            packingList.addItem("Snow Boots");
            packingList.addItem("Waterproof Pants");
        }
        
        return packingList;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Packing List:\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append(String.format("%d. %s%n", i + 1, items.get(i)));
        }
        return sb.toString();
    }
} 