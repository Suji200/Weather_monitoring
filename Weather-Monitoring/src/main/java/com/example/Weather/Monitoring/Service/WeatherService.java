package com.example.Weather.Monitoring.Service;

import com.example.Weather.Monitoring.Model.WeatherDataModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private WeatherDataModel weatherData;

    private static final String API_KEY = "c19fa8eb430961dbbc1346d73bbc1a45"; // Replace with your OpenWeatherMap API key
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public WeatherDataModel getWeatherData(String city) throws Exception {
        String url = String.format("%s?q=%s&appid=%s", BASE_URL, city, API_KEY);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            return parseWeatherData(jsonResponse);
        }
    }

    private WeatherDataModel parseWeatherData(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Extracting relevant data
        double tempKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
        double tempCelsius = tempKelvin - 273.15; // Convert from Kelvin to Celsius
        String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

        return new WeatherDataModel(tempCelsius, weatherCondition);
    }
}



