package com.weatherguard.producer.traveladvisor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Activator for Travel Advisory Producer Bundle
 */
public class Activator implements BundleActivator {
    
    private ServiceRegistration<?> registration;
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Travel Advisory Producer started.");
        
        // Create and register the service
        TravelAdvisoryService service = new TravelAdvisoryServiceImpl();
        registration = context.registerService(
            TravelAdvisoryService.class.getName(), 
            service, 
            null
        );
        
        System.out.println("Travel Advisory Service registered successfully.");
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Travel Advisory Producer stopping...");
        
        // Unregister the service
        if (registration != null) {
            registration.unregister();
            registration = null;
        }
        
        System.out.println("Travel Advisory Producer stopped.");
    }
}