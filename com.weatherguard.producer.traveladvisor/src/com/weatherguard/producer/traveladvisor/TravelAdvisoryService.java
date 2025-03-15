package com.weatherguard.producer.traveladvisor;

/**
 * Service interface for Travel Advisory
 */
public interface TravelAdvisoryService {
    
    /**
     * Get available travel categories
     * @return Array of available categories
     */
    String[] getCategories();
    
    /**
     * Get recommended destinations based on category
     * @param categoryNumber The index of the selected category
     * @return Recommended destinations
     */
    String[] getRecommendedDestinations(int categoryNumber);
    
    /**
     * Get recommended activities based on category
     * @param categoryNumber The index of the selected category
     * @return Recommended activities
     */
    String[] getRecommendedActivities(int categoryNumber);
    
    /**
     * Get potential risks based on category
     * @param categoryNumber The index of the selected category
     * @return Potential risks
     */
    String[] getPotentialRisks(int categoryNumber);
    
    /**
     * Get safety tips based on category
     * @param categoryNumber The index of the selected category
     * @return Safety tips
     */
    String[] getSafetyTips(int categoryNumber);
}
