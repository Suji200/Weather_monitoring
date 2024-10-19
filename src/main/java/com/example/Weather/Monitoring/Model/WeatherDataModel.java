package com.example.Weather.Monitoring.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "wheather")
public class WeatherDataModel {

//    @Id
//    private String id;
    private String city;
    private double temperature;
    private double humidity;
    private String weatherDescription;
    private long timestamp;

    private double averageTemp;
    private double minTemp;
    private double maxTemp;

    private String dominantWeatherCondition;

//    private double temperature;

    private String condition;

    public WeatherDataModel(String city, double averageTemp, double minTemp, double maxTemp, String dominantWeatherCondition) {
        this.averageTemp = averageTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.city =city;
        this.dominantWeatherCondition = dominantWeatherCondition;
    }



}

