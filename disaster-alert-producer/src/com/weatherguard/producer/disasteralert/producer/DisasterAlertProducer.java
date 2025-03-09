package com.weatherguard.producer.disasteralert.producer;

import com.weatherguard.producer.disasteralert.model.DisasterAlert;
import com.weatherguard.producer.disasteralert.service.DisasterAlertService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DisasterAlertProducer implements DisasterAlertService, BundleActivator {
    private ServiceRegistration<?> serviceRegistration;
    private Map<String, DisasterAlert> alertRegistry = new HashMap<>();
    private Random random = new Random();
    
    private String[] cities = {"Kandy", "Colombo", "Galle", "Jaffna", "Matara", "Anuradhapura", "Trincomalee"};
    private String[] alertTypes = {"FLOOD", "STORM", "LANDSLIDE", "EARTHQUAKE", "TSUNAMI", "DROUGHT"};
    private String[] severityLevels = {"LOW", "MEDIUM", "HIGH", "CRITICAL"};
    private String[] descriptions = {
        "Heavy flooding reported in central areas",
        "Tropical storm approaching, heavy rain expected",
        "Minor landslide risk in hill regions",
        "Earthquake detected, potential aftershocks",
        "Tsunami warning issued, coastal areas at risk",
        "Severe drought conditions, water shortages expected"
    };

    @Override
    public void start(BundleContext context) throws Exception {
        // Register the service when the bundle starts
        serviceRegistration = context.registerService(
            DisasterAlertService.class.getName(), 
            this, 
            null
        );
     
     // Broadcast a random alert when the bundle starts
        broadcastRandomAlert();
        
        System.out.println("Disaster Alert Producer Bundle Started");    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // Unregister the service when the bundle stops
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
        System.out.println("Disaster Alert Producer Bundle Stopped");
    }

    @Override
    public void broadcastAlert(String location, String alertType, String severityLevel, String description) {
        DisasterAlert alert = new DisasterAlert(location, alertType, severityLevel, description);
        alertRegistry.put(location, alert);
        System.out.printf("Broadcasted Alert: %s in %s%n", alertType, location);
    }

    @Override
    public DisasterAlert getLatestAlert(String location) {
        return alertRegistry.get(location);
    }
    
    private void broadcastRandomAlert() {
        String location = cities[random.nextInt(cities.length)];
        String alertType = alertTypes[random.nextInt(alertTypes.length)];
        String severityLevel = severityLevels[random.nextInt(severityLevels.length)];
        String description = descriptions[random.nextInt(descriptions.length)];

        broadcastAlert(location, alertType, severityLevel, description);
    }
}