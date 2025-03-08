package com.weatherguard.producer.weatherstation;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Enhanced implementation of the WeatherProducer interface
 * that generates realistic weather data at 5-second intervals.
 */
public class WeatherProducerImpl implements WeatherProducer {

    private final Random random = new Random();
    private final Timer timer = new Timer(true);
    private final AtomicReference<String> currentWeatherData = new AtomicReference<>("");
    
    // Weather data ranges
    private static final double MIN_TEMPERATURE = 15.0;  // °C
    private static final double MAX_TEMPERATURE = 35.0;  // °C
    private static final double MAX_RAINFALL = 8.0;      // mm
    private static final double MIN_HUMIDITY = 30.0;     // %
    private static final double MAX_HUMIDITY = 90.0;     // %
    private static final double MIN_WIND_SPEED = 0.0;    // km/h
    private static final double MAX_WIND_SPEED = 30.0;   // km/h
    private static final double MIN_PRESSURE = 990.0;    // hPa
    private static final double MAX_PRESSURE = 1030.0;   // hPa
    
    /**
     * Constructor initializes the weather data and starts the timer
     * to update weather data every 5 seconds.
     */
    public WeatherProducerImpl() {
        // Initialize with first weather reading
        updateWeatherData();
        
        // Schedule updates every 5 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateWeatherData();
            }
        }, 5000, 5000);
    }
    
    /**
     * Updates the current weather data with new random values.
     */
    private void updateWeatherData() {
        double temperature = MIN_TEMPERATURE + random.nextDouble() * (MAX_TEMPERATURE - MIN_TEMPERATURE);
        double rainfall = random.nextDouble() * MAX_RAINFALL;
        double humidity = MIN_HUMIDITY + random.nextDouble() * (MAX_HUMIDITY - MIN_HUMIDITY);
        double windSpeed = MIN_WIND_SPEED + random.nextDouble() * (MAX_WIND_SPEED - MIN_WIND_SPEED);
        double pressure = MIN_PRESSURE + random.nextDouble() * (MAX_PRESSURE - MIN_PRESSURE);
        String windDirection = getRandomWindDirection();
        
        String formattedData = String.format(
            "Weather Report:\n" +
            "---------------\n" +
            "Temperature: %.1f°C\n" +
            "Rainfall: %.1f mm\n" +
            "Humidity: %.1f%%\n" +
            "Wind: %.1f km/h %s\n" +
            "Pressure: %.1f hPa\n" +
            "Timestamp: %s",
            temperature, rainfall, humidity, windSpeed, windDirection, pressure, 
            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        
        currentWeatherData.set(formattedData);
    }
    
    /**
     * Generates a random wind direction as a cardinal point.
     * 
     * @return A string representing wind direction (N, NE, E, etc.)
     */
    private String getRandomWindDirection() {
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        return directions[random.nextInt(directions.length)];
    }

    /**
     * Returns the current weather data.
     * 
     * @return Formatted string containing current weather metrics
     */
    @Override
    public String getWeatherData() {
        return currentWeatherData.get();
    }
    
    /**
     * Stops the timer when the object is no longer needed.
     * Should be called to prevent memory leaks.
     */
    public void shutdown() {
        timer.cancel();
    }
}