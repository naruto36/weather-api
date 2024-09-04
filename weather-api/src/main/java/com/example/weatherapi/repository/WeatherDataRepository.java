package com.example.weatherapi.repository;

import com.example.weatherapi.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    WeatherData findByCityNameAndCountryName(String cityName, String countryName);
}
