package com.example.Weather.Monitoring.Service;

import com.example.Weather.Monitoring.Model.WeatherDataModel;
import com.example.Weather.Monitoring.Repository.WeatherRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherService {
    private  WeatherDataModel weatherDataModel;
    @Autowired
    private WeatherRepository weatherRepository;


    private static final String API_KEY = "c19fa8eb430961dbbc1346d73bbc1a45"; // Replace with your OpenWeatherMap API key
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private List<String> cities = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");

    // Method to fetch and process weather data for multiple cities at a scheduled interval
    @Scheduled(fixedRate = 300000) // 300000 ms = 5 minutes
    public void fetchWeatherDataForCities() throws Exception {
        for (String city : cities) {
            WeatherDataModel weatherData = getWeatherData(city);
            System.out.println("Weather Data for " + city + ": " + weatherData);
            weatherRepository.save(weatherData);
        }
    }

    // Method to get weather data for a specific city
    public WeatherDataModel getWeatherData(String city) throws Exception {
        String url = String.format("%s?q=%s&appid=%s", BASE_URL, city, API_KEY);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            return parseWeatherData(jsonResponse, city);
        }
    }

    // Parse weather data and handle temperature conversions
    private WeatherDataModel parseWeatherData(String jsonResponse, String city) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Ensure that the "main" object exists
        if (!jsonObject.has("main") || jsonObject.get("main").isJsonNull()) {
            throw new IllegalArgumentException("Main weather data is missing in the API response.");
        }

        // Extract temperature data in Kelvin
        double tempKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
        double tempMinKelvin = jsonObject.getAsJsonObject("main").get("temp_min").getAsDouble();
        double tempMaxKelvin = jsonObject.getAsJsonObject("main").get("temp_max").getAsDouble();

        // User preference for temperature units (can be Kelvin, Celsius, or Fahrenheit)

        String userPreference = getUserTemperaturePreference(); // Example: "Celsius" or "Fahrenheit"
        double temp = convertTemperature(tempKelvin, userPreference);
        double tempMin = convertTemperature(tempMinKelvin, userPreference);
        double tempMax = convertTemperature(tempMaxKelvin, userPreference);

        // Extract weather condition
        String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

        return new WeatherDataModel(city, temp, tempMin, tempMax, weatherCondition);
    }

    // Convert temperature from Kelvin to user's preferred unit
    private double convertTemperature(double tempKelvin, String userPreference) {
        if ("Celsius".equalsIgnoreCase(userPreference)) {
            return kelvinToCelsius(tempKelvin);
        } else if ("Fahrenheit".equalsIgnoreCase(userPreference)) {
            return kelvinToFahrenheit(tempKelvin);
        }
        return tempKelvin; // Return Kelvin by default
    }

    // Helper method to convert Kelvin to Celsius
    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    // Helper method to convert Kelvin to Fahrenheit
    private double kelvinToFahrenheit(double kelvin) {
        return (kelvin - 273.15) * 9/5 + 32;
    }
    // Mock method to simulate user preference retrieval (can be stored in DB or user session)
    private String getUserTemperaturePreference() {
        // You can modify this method to return the actual user preference (e.g., from a DB)
        return "Celsius"; // Example: returning Celsius as default (can be "Celsius", "Fahrenheit", or "Kelvin")
    }


}


    // Mock method to simulate user preference retrieval (can be stored in DB or user sessio




