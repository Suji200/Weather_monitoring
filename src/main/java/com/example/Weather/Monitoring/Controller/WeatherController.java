package com.example.Weather.Monitoring.Controller;

import com.example.Weather.Monitoring.Model.WeatherDataModel;
import com.example.Weather.Monitoring.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherDataModel getWeather(@RequestParam String city) {
        try {
            return weatherService.getWeatherData(city);
        } catch (Exception e) {
            // Handle exception, log error, or return a custom error response
            e.printStackTrace();
            return null; // You might want to return an appropriate error response instead
        }
    }
}
