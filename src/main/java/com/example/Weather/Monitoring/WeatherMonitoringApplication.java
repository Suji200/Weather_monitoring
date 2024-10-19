package com.example.Weather.Monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherMonitoringApplication.class, args);
	}

}
