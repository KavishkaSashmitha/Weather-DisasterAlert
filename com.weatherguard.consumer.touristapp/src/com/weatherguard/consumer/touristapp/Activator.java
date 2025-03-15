package com.weatherguard.consumer.touristapp;

import java.util.Dictionary;
import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import com.weatherguard.producer.traveladvisor.TravelAdvisoryService;

/**
 * Activator for Tourist App Consumer Bundle with proper OSGi Command support
 */
public class Activator implements BundleActivator {
    
    private ServiceReference<?> serviceReference;
    private TravelAdvisoryService travelAdvisoryService;
    private ServiceRegistration<?> commandRegistration;
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Tourist App Consumer started.");
        
        // Get the Travel Advisory Service
        serviceReference = context.getServiceReference(TravelAdvisoryService.class.getName());
        
        if (serviceReference != null) {
            travelAdvisoryService = (TravelAdvisoryService) context.getService(serviceReference);
            System.out.println("Travel Advisory Service acquired successfully.");
            
            // Register commands for the Tourist App
            registerCommands(context);
            
            // Display initial menu
            displayMenu();
        } else {
            System.out.println("Travel Advisory Service not available.");
        }
    }
    
    private void registerCommands(BundleContext context) {
        // Create command implementation
        TouristAppCommands commands = new TouristAppCommands(travelAdvisoryService);
        
        // Prepare properties for command service registration
        Dictionary<String, Object> props = new Hashtable<>();
        props.put("osgi.command.scope", "tourist");
        props.put("osgi.command.function", new String[] { "menu", "info" });
        
        // Register the command service with the OSGi service registry
        // This registers the class under the OSGi Command service interface
        commandRegistration = context.registerService(
            Object.class.getName(),  // Register as generic Object
            commands,                // The service implementation
            props                    // Service properties for command
        );
        
        System.out.println("Tourist App commands registered:");
        System.out.println("  tourist:menu - Display the main menu");
        System.out.println("  tourist:info <category> - Show information for a category (0-4)");
    }
    
    private void displayMenu() {
        System.out.println("\n========== TOURIST APP ==========");
        System.out.println("Available Travel Categories:");
        
        String[] categories = travelAdvisoryService.getCategories();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i) + ". " + categories[i]);
        }
        
        System.out.println("\nCommands:");
        System.out.println("tourist:menu - Show this menu again");
        System.out.println("tourist:info <number> - Show details for a category (e.g., tourist:info 0)");
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Tourist App Consumer stopping...");
        
        // Unregister command service
        if (commandRegistration != null) {
            commandRegistration.unregister();
            commandRegistration = null;
        }
        
        // Release the service
        if (serviceReference != null) {
            context.ungetService(serviceReference);
            serviceReference = null;
            travelAdvisoryService = null;
        }
        
        System.out.println("Tourist App Consumer stopped.");
    }
}

/**
 * Command implementation for the Tourist App
 * 
 * Each public method becomes an available command
 */
class TouristAppCommands {
    
    private TravelAdvisoryService travelAdvisoryService;
    
    public TouristAppCommands(TravelAdvisoryService travelAdvisoryService) {
        this.travelAdvisoryService = travelAdvisoryService;
    }
    
    /**
     * Display the main menu
     */
    public void menu() {
        System.out.println("\n========== TOURIST APP ==========");
        System.out.println("Available Travel Categories:");
        
        String[] categories = travelAdvisoryService.getCategories();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i) + ". " + categories[i]);
        }
        
        System.out.println("\nCommands:");
        System.out.println("tourist:menu - Show this menu again");
        System.out.println("tourist:info <number> - Show details for a category (e.g., tourist:info 0)");
    }
    
    /**
     * Show information about a specific travel category
     * 
     * @param categoryNumber The category number to display info for
     */
    public void info(String categoryNumber) {
        try {
            int category = Integer.parseInt(categoryNumber);
            
            if (category >= 0 && category < travelAdvisoryService.getCategories().length) {
                String categoryName = travelAdvisoryService.getCategories()[category];
                
                System.out.println("\n===== " + categoryName + " =====");
                
                System.out.println("\nRECOMMENDED DESTINATIONS:");
                printArray(travelAdvisoryService.getRecommendedDestinations(category));
                
                System.out.println("\nRECOMMENDED ACTIVITIES:");
                printArray(travelAdvisoryService.getRecommendedActivities(category));
                
                System.out.println("\nPOTENTIAL RISKS:");
                printArray(travelAdvisoryService.getPotentialRisks(category));
                
                System.out.println("\nSAFETY TIPS:");
                printArray(travelAdvisoryService.getSafetyTips(category));
            } else {
                System.out.println("Invalid category number. Valid options are 0-" + 
                    (travelAdvisoryService.getCategories().length - 1));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number (e.g., tourist:info 0)");
        }
    }
    
    private void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("- " + array[i]);
        }
    }
}