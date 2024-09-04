package com.example.weatherapi.config;

import java.util.HashMap;
import java.util.Map;

public class ApiKeyConfig {
    public static Map<String, Integer> API_KEYS = new HashMap<>();

    static {
        API_KEYS.put("key1", 0);
        API_KEYS.put("key2", 0);
        API_KEYS.put("key3", 0);
        API_KEYS.put("key4", 0);
        API_KEYS.put("key5", 0);
    }

    public static final int LIMIT_PER_HOUR = 5;
}
