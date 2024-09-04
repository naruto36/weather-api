package com.example.weatherapi.service;

import com.example.weatherapi.config.ApiKeyConfig;
import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherService(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository) {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
    }

    public String getWeather(String cityName, String countryName, String apiKey) {
        if (!ApiKeyConfig.API_KEYS.containsKey(apiKey) ||
                ApiKeyConfig.API_KEYS.get(apiKey) >= ApiKeyConfig.LIMIT_PER_HOUR) {
            return "Invalid API Key or Rate Limit Exceeded";
        }

        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,%s&appid=%s",
                cityName, countryName, this.apiKey);

        WeatherData cachedData = weatherDataRepository.findByCityNameAndCountryName(cityName, countryName);
        if (cachedData != null) {
            return cachedData.getDescription();
        }

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        String description = ((Map<String, Object>) ((Map<String, Object>) response.get("weather")).get(0)).get("description").toString();

        WeatherData weatherData = new WeatherData();
        weatherData.setCityName(cityName);
        weatherData.setCountryName(countryName);
        weatherData.setDescription(description);
        weatherDataRepository.save(weatherData);

        ApiKeyConfig.API_KEYS.put(apiKey, ApiKeyConfig.API_KEYS.get(apiKey) + 1);

        return description;
    }
}
