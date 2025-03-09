package com.weatherguard.producer.weatherstation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

/**
 * WeatherProducer implementation that generates realistic weather data
 * at 5-second intervals, including city, temperature, rainfall, humidity,
 * wind speed, wind direction, and atmospheric pressure.
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

    private static final String[] CITIES = {
    		"Kandy", "Colombo", "Galle", "Sigiriya"
    };

    /**
     * Constructor initializes the weather data and starts a timer
     * to update weather data every 5 seconds.
     */
    public WeatherProducerImpl() {
        updateWeatherData();
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
        String city = getRandomCity();
        
        String formattedData = String.format(
            "\n=============== WEATHER REPORT ===============\n" +
            " Location    : %-25s\n" +
            " Temperature : %-5.1f°C\n" +
            " Rainfall    : %-5.1f mm\n" +
            " Humidity    : %-5.1f%%\n" +
            " Wind        : %-5.1f km/h %s\n" +
            " Pressure    : %-5.1f hPa\n" +
            " Timestamp   : %s\n" +
            "============================================\n",
            city, temperature, rainfall, humidity, windSpeed, windDirection, pressure,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        
        currentWeatherData.set(formattedData);
    }

    /**
     * Generates a random wind direction as a cardinal point.
     * @return A string representing wind direction (N, NE, E, etc.)
     */
    private String getRandomWindDirection() {
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        return directions[random.nextInt(directions.length)];
    }

    /**
     * Selects a random city from the predefined list.
     * @return A random city name.
     */
    private String getRandomCity() {
        return CITIES[random.nextInt(CITIES.length)];
    }

    /**
     * Returns the current weather data.
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
