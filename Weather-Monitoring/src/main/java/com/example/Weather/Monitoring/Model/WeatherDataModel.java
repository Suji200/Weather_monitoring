package com.example.Weather.Monitoring.Model;

public class WeatherDataModel {

    private double temperature;
    private String condition;

    public WeatherDataModel(double temperature, String condition) {
        this.temperature = temperature;
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }
}
