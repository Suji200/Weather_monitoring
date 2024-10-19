package com.example.Weather.Monitoring.Repository;

import com.example.Weather.Monitoring.Model.WeatherDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends MongoRepository<WeatherDataModel, String> {

}
