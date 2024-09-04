package com.example.weatherapi.controller;

import com.example.weatherapi.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city,
                             @RequestParam String country,
                             @RequestParam String apiKey) {
        if (city == null || country == null || apiKey == null) {
            return "Invalid Input";
        }

        return weatherService.getWeather(city, country, apiKey);
    }
}
