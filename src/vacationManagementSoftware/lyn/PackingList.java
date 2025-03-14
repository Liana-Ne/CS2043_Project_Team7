package vacationManagementSoftware.lyn;

import java.util.ArrayList;
import java.util.List;

public class PackingList {
	 private String destination;
	 private int tripLength;
	 private String season;

	public PackingList(String destination, int tripLength, String season) {
	    this.destination = destination;
	    this.tripLength = tripLength;
	    this.season = season;
	    }

	public List<String> generatePackingList() {
	        List<String> items = new ArrayList<>();
	        items.add("Clothes");
	        items.add("Toiletries");
	        items.add("Passport");

	        if (tripLength > 5) {
	            items.add("Extra Shoes");
	        }
	        
	     switch(destination.toLowerCase()) {
	        case "paris":
	           if(season.equalsIgnoreCase("winter")) {
	                    items.add("Umbrella");
	                    items.add("Scarf");
	                    items.add("Gloves");
	                
	            } 
	          else if(season.equalsIgnoreCase("summer")) {
	                    items.add("Sunglasses");
	                    items.add("Hat");
	           } 
	           
	          else {
	                    items.add("Umbrella");
	           }
	               
	           break;
	           
	        case "london":
	             if(season.equalsIgnoreCase("winter")) {
	                    items.add("Raincoat");
	                    items.add("Umbrella");
	                    items.add("Warm Coat");
	              }
	             
	             else {
	                    items.add("Raincoat");
	                    items.add("Umbrella");
	                }
	             
	            break;
	            
	         default:
	                items.add("Check local weather for season-specific items");
	                break;
	        }
	        
	        return items;
	    }
	    
}
