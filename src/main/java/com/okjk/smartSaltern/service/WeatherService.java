package com.okjk.smartSaltern.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.okjk.smartSaltern.dto.SensorType;
import com.okjk.smartSaltern.dto.WeatherItem;
import com.okjk.smartSaltern.entity.TbEnv;
import com.okjk.smartSaltern.repository.TbEnvRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final TbEnvRepository tbEnvRepository;
    
    public WeatherService(TbEnvRepository tbEnvRepository) {
        this.tbEnvRepository = tbEnvRepository;
    }

    public void saveWeatherData(List<WeatherItem> items, Long sensorId, Long saltPondId) {
        for (WeatherItem item : items) {
            try {
                double sensorTypeCode = SensorType.fromCategory(item.getCategory());
                BigDecimal sensorVal = new BigDecimal(item.getObsrValue());

                TbEnv env = new TbEnv();
                env.setSensorId(sensorId);
                env.setSaltPondId(saltPondId);
                env.setSensorType(BigDecimal.valueOf(sensorTypeCode));
                env.setSensorVal(sensorVal);
                env.setMeasureDatetime(LocalDateTime.now());
                env.setCreateDate(LocalDateTime.now());

                tbEnvRepository.save(env);
            } catch (Exception e) {
                // 로그로 남기기
                System.err.println("기상 데이터 저장 실패: " + item + " / " + e.getMessage());
            }
        }
    }
}