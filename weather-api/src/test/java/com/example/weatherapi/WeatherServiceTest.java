package com.example.weatherapi.service;

import com.example.weatherapi.config.ApiKeyConfig;
import com.example.weatherapi.model.WeatherData;
import com.example.weatherapi.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherWithCachedData() {
        // Arrange
        String cityName = "Zocca";
        String countryName = "IT";
        String apiKey = "valid_api_key";

        WeatherData cachedData = new WeatherData();
        cachedData.setCityName(cityName);
        cachedData.setCountryName(countryName);
        cachedData.setDescription("moderate rain");

        when(weatherDataRepository.findByCityNameAndCountryName(cityName, countryName))
                .thenReturn(cachedData);

        // Act
        String description = weatherService.getWeather(cityName, countryName, apiKey);

        // Assert
        assertEquals("moderate rain", description);
        verify(restTemplate, never()).getForObject(anyString(), eq(Map.class));
    }

    @Test
    public void testGetWeatherWithoutCachedData() {
        // Arrange
        String cityName = "Zocca";
        String countryName = "IT";
        String apiKey = "valid_api_key";
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,%s&appid=%s",
                cityName, countryName, apiKey);

        Map<String, Object> weatherMap = new HashMap<>();
        weatherMap.put("description", "moderate rain");

        Map<String, Object> response = new HashMap<>();
        response.put("weather", new Object[]{weatherMap});

        when(weatherDataRepository.findByCityNameAndCountryName(cityName, countryName))
                .thenReturn(null);

        when(restTemplate.getForObject(url, Map.class))
                .thenReturn(response);

        // Act
        String description = weatherService.getWeather(cityName, countryName, apiKey);

        // Assert
        assertEquals("moderate rain", description);
        verify(weatherDataRepository).save(any(WeatherData.class));
    }

    @Test
    public void testGetWeatherInvalidApiKey() {
        // Arrange
        String cityName = "Zocca";
        String countryName = "IT";
        String apiKey = "invalid_api_key";

        ApiKeyConfig.API_KEYS = new HashMap<>();
        ApiKeyConfig.API_KEYS.put(apiKey, 0);

        // Act
        String result = weatherService.getWeather(cityName, countryName, apiKey);

        // Assert
        assertEquals("Invalid API Key or Rate Limit Exceeded", result);
        verify(restTemplate, never()).getForObject(anyString(), eq(Map.class));
        verify(weatherDataRepository, never()).save(any(WeatherData.class));
    }

    @Test
    public void testGetWeatherRateLimitExceeded() {
        // Arrange
        String cityName = "Zocca";
        String countryName = "IT";
        String apiKey = "valid_api_key";

        ApiKeyConfig.API_KEYS = new HashMap<>();
        ApiKeyConfig.API_KEYS.put(apiKey, ApiKeyConfig.LIMIT_PER_HOUR);

        // Act
        String result = weatherService.getWeather(cityName, countryName, apiKey);

        // Assert
        assertEquals("Invalid API Key or Rate Limit Exceeded", result);
        verify(restTemplate, never()).getForObject(anyString(), eq(Map.class));
        verify(weatherDataRepository, never()).save(any(WeatherData.class));
    }
}
