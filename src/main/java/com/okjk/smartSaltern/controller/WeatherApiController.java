package com.okjk.smartSaltern.controller;

import com.okjk.smartSaltern.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherApiController {

    private final WeatherApiService weatherApiService;
    
    public WeatherApiController(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }
    
    
    @GetMapping("/forecast/save/auto")
    public ResponseEntity<String> fetchAndSaveAuto(
            @RequestParam int nx,
            @RequestParam int ny,
            @RequestParam(required = false, defaultValue = "1") Long spIdx
    ) {
        Map<String, String> base = weatherApiService.getLatestBaseDateTime();
        String json = weatherApiService.getVilageFcst(base.get("baseDate"), base.get("baseTime"), nx, ny);
        weatherApiService.saveToTbEnv(json, spIdx);
        return ResponseEntity.ok("자동 기준 시간으로 예보 저장 완료!");
    }
}

