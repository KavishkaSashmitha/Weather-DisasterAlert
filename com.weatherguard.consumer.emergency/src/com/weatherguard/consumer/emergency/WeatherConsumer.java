package com.weatherguard.consumer.emergency;

import com.weatherguard.producer.weatherstation.WeatherProducer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherConsumer implements BundleActivator {

    private ServiceReference<WeatherProducer> serviceReference;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(BundleContext context) {
        serviceReference = context.getServiceReference(WeatherProducer.class);
        if (serviceReference == null) {
            System.out.println("WeatherProducer service not available.");
            return;
        }

        WeatherProducer weatherProducer = context.getService(serviceReference);
        if (weatherProducer == null) {
            System.out.println("Could not get WeatherProducer service.");
            return;
        }

        scheduler.scheduleAtFixedRate(() -> {
            String weatherData = weatherProducer.getWeatherData();
            System.out.println("Weather Data Received: \n" + weatherData);
            analyzeWeather(weatherData);
        }, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void stop(BundleContext context) {
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
        scheduler.shutdown();
        System.out.println("WeatherConsumer stopped.");
    }

    private void analyzeWeather(String weatherData) {
    if (weatherData == null || weatherData.trim().isEmpty()) {
        System.out.println("No weather data available yet.");
        return;
    }

    System.out.println("Raw Weather Data:\n" + weatherData);
    String[] lines = weatherData.trim().split("\n");

    if (lines.length < 7) { // Ensure enough lines exist
        System.out.println("Weather data format incorrect: \n" + weatherData);
        return;
    }

    try {
        // Extract temperature (now at index 2)
        String[] tempParts = lines[2].split(": ");
        if (tempParts.length < 2) {
            System.out.println("Temperature data format incorrect: " + lines[2]);
            return;
        }
        double temperature = Double.parseDouble(tempParts[1].replace("°C", "").trim());

        // Extract rainfall (now at index 3)
        String[] rainParts = lines[3].split(": ");
        if (rainParts.length < 2) {
            System.out.println("Rainfall data format incorrect: " + lines[3]);
            return;
        }
        double rainfall = Double.parseDouble(rainParts[1].replace("mm", "").trim());

        // Alert conditions
        if (temperature > 35) {
            System.out.println("[ALERT] Extreme Heat! Stay hydrated and avoid outdoor activities.");
        }
        if (rainfall > 4.0) {
            System.out.println("[ALERT] Heavy Rainfall! Risk of flooding, take precautions.");
        }
        else {
        	System.out.println("There are no emergency situations at the moment.");
        }

    } catch (Exception e) {
        System.out.println("Error parsing weather data: " + e.getMessage());
    }
}

}
