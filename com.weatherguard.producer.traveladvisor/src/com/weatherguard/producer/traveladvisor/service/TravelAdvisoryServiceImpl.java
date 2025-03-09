package com.weatherguard.producer.traveladvisor.service;

import com.weatherguard.producer.traveladvisor.model.TravelAdvisor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Travel Advisory Service
 */
public class TravelAdvisoryServiceImpl implements TravelAdvisoryService {
    
    private final Map<String, TravelAdvisor> advisoryDatabase = new HashMap<>();
    private final List<String> categories = Arrays.asList("nature", "adventure", "historical", "cultural");
    
    public TravelAdvisoryServiceImpl() {
        initializeDatabase();
    }
    
    /**
     * Initialize the travel advisory database with sample data
     */
    private void initializeDatabase() {
        // Nature category
        List<String> natureDestinations = Arrays.asList("Yellowstone National Park", "Costa Rica", "New Zealand", "Norway Fjords");
        List<String> natureActivities = Arrays.asList("Hiking", "Wildlife watching", "Photography", "Camping");
        advisoryDatabase.put("nature", new TravelAdvisor("nature", natureDestinations, natureActivities, 
                "Check weather conditions before hiking. Maintain safe distance from wildlife."));
        
        // Adventure category
        List<String> adventureDestinations = Arrays.asList("Swiss Alps", "Grand Canyon", "Bali", "Queenstown");
        List<String> adventureActivities = Arrays.asList("Paragliding", "White water rafting", "Rock climbing", "Bungee jumping");
        advisoryDatabase.put("adventure", new TravelAdvisor("adventure", adventureDestinations, adventureActivities,
                "Always use certified guides and equipment. Check insurance coverage for extreme sports."));
        
        // Historical category
        List<String> historicalDestinations = Arrays.asList("Rome", "Athens", "Machu Picchu", "Kyoto");
        List<String> historicalActivities = Arrays.asList("Guided tours", "Museum visits", "Archaeological sites", "Historical walks");
        advisoryDatabase.put("historical", new TravelAdvisor("historical", historicalDestinations, historicalActivities,
                "Respect heritage sites. Some locations may have dress codes or photography restrictions."));
        
        // Cultural category
        List<String> culturalDestinations = Arrays.asList("Marrakech", "Tokyo", "New Orleans", "Barcelona");
        List<String> culturalActivities = Arrays.asList("Food tours", "Festival attendance", "Art galleries", "Local workshops");
        advisoryDatabase.put("cultural", new TravelAdvisor("cultural", culturalDestinations, culturalActivities,
                "Research local customs and etiquette. Try to learn a few phrases in the local language."));
    }
    
    @Override
    public TravelAdvisor getRecommendations(String category) {
        if (category == null || !categories.contains(category.toLowerCase())) {
            return createDefaultAdvisory();
        }
        return advisoryDatabase.get(category.toLowerCase());
    }
    
    @Override
    public List<String> getAvailableCategories() {
        return new ArrayList<>(categories);
    }
    
    /**
     * Create a default advisory when an invalid category is requested
     */
    private TravelAdvisor createDefaultAdvisory() {
        return new TravelAdvisor("general", 
            Arrays.asList("Paris", "Tokyo", "New York", "Sydney"),
            Arrays.asList("Sightseeing", "Local cuisine", "Shopping", "Museums"),
            "Always keep your travel documents secure and register with your embassy when traveling abroad."
        );
    }
}
