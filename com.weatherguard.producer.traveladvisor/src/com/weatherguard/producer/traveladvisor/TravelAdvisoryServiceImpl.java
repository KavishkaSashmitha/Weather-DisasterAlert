package com.weatherguard.producer.traveladvisor;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Travel Advisory Service
 */
public class TravelAdvisoryServiceImpl implements TravelAdvisoryService {
    
    private final String[] categories = {
        "Nature & Wildlife", 
        "Beach Vacation", 
        "Religious & Pilgrimage", 
        "Adventure Travel", 
        "Cultural Exploration"
    };
    
    private final Map<Integer, String[]> destinations = new HashMap<>();
    private final Map<Integer, String[]> activities = new HashMap<>();
    private final Map<Integer, String[]> risks = new HashMap<>();
    private final Map<Integer, String[]> safetyTips = new HashMap<>();
    
    public TravelAdvisoryServiceImpl() {
        initializeData();
    }
    
    private void initializeData() {
        // Initialize Nature & Wildlife (0)
        destinations.put(0, new String[]{"Yala", "Udawalawa", "Minneriya"});
        activities.put(0, new String[]{"Safari tours", "Bird watching", "Photography", "Camping"});
        risks.put(0, new String[]{"Heavy rains causing flash floods."});
        safetyTips.put(0, new String[]{
        	"Visit during the December-April for Yala", 
            "Stay hydrated", 
            "Visit during the June-September for Minneriya.", 
            "Check weather forecasts daily"
        });
        
        // Initialize Beach Vacation (1)
        destinations.put(1, new String[]{"Weligama", "Hiriketiya", "Galle","Mirissa","Unawatuna"});
        activities.put(1, new String[]{"Whale watching", "Surfing", "Boat trip", "Swimming"});
        risks.put(1, new String[]{
            "Cyclones during October-December", 
            "Tsunami risks"
            
        });
        safetyTips.put(1, new String[]{
        	"Follow lifeguard instructions", 
            "avoid the sea during storms", 
        });
        
        // Initialize Religious & Pilgrimage (2)
        destinations.put(2, new String[]{"Adam's peak", "Dambulla", "Mihintale"});
        activities.put(2, new String[]{
            "Exploring sacred caves", 
            "Climbing adam's peak", 
            "Festival attendance", 
            "Attending cultural events"
        });
        risks.put(2, new String[]{
            "Heavy rain", 
            "fog affecting visibility", 
           
        });
        safetyTips.put(2, new String[]{
            "Start hikes early", 
            "carry a flashlight for night pilrimages", 
            "Be aware of surroundings", 
            "Have emergency contacts available"
        });
        
        // Initialize Adventure Travel (3)
        destinations.put(3, new String[]{"Ella", "Knuckles Mountain Range", "Kitulgala"});
        activities.put(3, new String[]{
            "Bungee jumping", 
            "White water rafting", 
            "Zip-lining", 
            "Rock climbing", 
            "Cave exploration"
        });
        risks.put(3, new String[]{
            "Flash floods", 
            "lanfdslides during monsoon seasons", 
            "Slippery trails during rainy periods", 
             
            "Extreme weather conditions"
        });
        safetyTips.put(3, new String[]{
            "Use reputable tour operators", 
            "Wear proper hiking or rafting gear", 
            "Follow guide instructions closely", 
            "Check weather forecasts before planning hikes"
            
        });
        
        // Initialize Cultural Exploration (4)
        destinations.put(4, new String[]{"Sigiriya", "Polonnaruwa", "Anuradhapura"});
        activities.put(4, new String[]{
            "Temple/museum visits", 
            "Local cuisine sampling", 
            "Guided tours", 
            "Historical site tours", 
            "Attending cultural festivals"
        });
        risks.put(4, new String[]{
            "High temperature", 
            "heatstroke risks", 
            "Unpredictable thunderstorms during the monsoon seasons"
            
        });
        safetyTips.put(4, new String[]{
            "Stay hydrated", 
            "wear sun protection", 
            "Avoid visiting during extreme weather conditions"
            
        });
    }
    
    @Override
    public String[] getCategories() {
        return categories;
    }
    
    @Override
    public String[] getRecommendedDestinations(int categoryNumber) {
        if (categoryNumber >= 0 && categoryNumber < categories.length) {
            return destinations.get(categoryNumber);
        }
        return new String[]{"Invalid category selection"};
    }
    
    @Override
    public String[] getRecommendedActivities(int categoryNumber) {
        if (categoryNumber >= 0 && categoryNumber < categories.length) {
            return activities.get(categoryNumber);
        }
        return new String[]{"Invalid category selection"};
    }
    
    @Override
    public String[] getPotentialRisks(int categoryNumber) {
        if (categoryNumber >= 0 && categoryNumber < categories.length) {
            return risks.get(categoryNumber);
        }
        return new String[]{"Invalid category selection"};
    }
    
    @Override
    public String[] getSafetyTips(int categoryNumber) {
        if (categoryNumber >= 0 && categoryNumber < categories.length) {
            return safetyTips.get(categoryNumber);
        }
        return new String[]{"Invalid category selection"};
    }
}