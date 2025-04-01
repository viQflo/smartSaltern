package com.okjk.smartSaltern.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okjk.smartSaltern.entity.TbEnv;
import com.okjk.smartSaltern.repository.TbEnvRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherDataController {

    private final TbEnvRepository tbEnvRepository;
    
    public WeatherDataController(TbEnvRepository tbEnvRepository) {
        this.tbEnvRepository = tbEnvRepository;
    }

    @GetMapping("/env-data")
    public Map<String, List<Map<String, Object>>> getChartData() {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();

        Map<Long, String> sensorNames = Map.of(
            101L, "TMP", 102L, "REH", 103L, "WSD", 104L, "PCP", 105L, "SUN"
        );

        for (Long sensorId : sensorNames.keySet()) {
            List<TbEnv> dataList = tbEnvRepository.findTop20BySensorIdOrderByMeasureDatetimeDesc(sensorId);
            List<Map<String, Object>> chartData = new ArrayList<>();

            for (TbEnv env : dataList) {
                chartData.add(Map.of(
                    "time", env.getMeasureDatetime().toString(),
                    "value", env.getSensorVal()
                ));
            }

            result.put(sensorNames.get(sensorId), chartData);
        }

        return result;
    }
}