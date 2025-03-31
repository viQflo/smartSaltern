package com.okjk.smartSaltern.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.okjk.smartSaltern.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherApiController {

    private final WeatherApiService weatherApiService;
    
    public WeatherApiController(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @GetMapping("/test")
    public JsonNode getTestWeatherData(
            @RequestParam(defaultValue = "55") double nx,
            @RequestParam(defaultValue = "127") double ny
    ) {
        return weatherApiService.getWeatherData(nx, ny);
    }
}