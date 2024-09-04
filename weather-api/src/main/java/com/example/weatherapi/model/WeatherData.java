package com.example.weatherapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;
    private String countryName;
    private String description;

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for cityName
    public String getCityName() {
        return cityName;
    }

    // Setter for cityName
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    // Getter for countryName
    public String getCountryName() {
        return countryName;
    }

    // Setter for countryName
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }
}
