# CS2043_Project_Team7
Vacation Management Software - planning flights, accommodations, and itineraries

Spike: Smart Packing Assistant Using Weather API  

Spike Overview  
This spike explores how weather data can influence packing recommendations for travelers. It integrates the OpenWeather API to fetch real-time weather conditions and suggests clothing based on temperature and weather conditions.  

Spike Details  
- Spike Name: Smart Packing Assistant Using Weather API  
- Spike Owners: Syed Abbas  
- Spike Branch Name: `spike-weather-packing`  
- GitHub Branch Link: [spike-weather-packing](https://github.com/Liana-Ne/CS2043_Project_Team7/tree/spike-weather-packing)  
- Jira Spike Card: https://cs2043-team7-projectdesign.atlassian.net/browse/SCRUM-22?atlOrigin=eyJpIjoiMDJmNGM3MjM0ZjlkNDg3ODlhYWJmMmJhMzk2ZmUxMDAiLCJwIjoiaiJ9

Goals  
- Integrate OpenWeather API to fetch real-time weather forecasts for a given travel destination.  
- Use the weather conditions to generate packing recommendations for the user.  
- Ensure the system returns accurate clothing suggestions based on the weather.  

Outcome  
- Successfully integrated OpenWeather API.  
- Implemented logic for weather-based packing recommendations.  
- Returns recommendations based on temperature and weather conditions.  
- All code is available on the `spike-weather-packing` branch.  

API Usage  
To test the API, use the following endpoint:  
http://localhost:8080/weather?destination=Toronto

Example Response:  

```json
{
    "destination": "Toronto",
    "temperature": "3.55°C",
    "condition": "overcast clouds",
    "packing_recommendations": [
        "Jacket",
        "Gloves",
        "Thermal wear",
        "Boots"
    ]
}

