package com.okjk.smartSaltern.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.okjk.smartSaltern.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherApiController {

    private final WeatherApiService weatherApiService;

    public WeatherApiController(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @GetMapping("/forecast")
    public ResponseEntity<String> getForecast() {
        String response = weatherApiService.getVilageFcst("20250331", "0500", 60, 127);
        return ResponseEntity.ok(response);
    }
}