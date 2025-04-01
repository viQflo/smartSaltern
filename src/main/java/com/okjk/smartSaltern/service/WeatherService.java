package com.okjk.smartSaltern.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                // category -> sensorType 매핑
                double sensorTypeCode = SensorType.fromCategory(item.getCategory());
                BigDecimal sensorVal = new BigDecimal(item.getFcstValue());

                // fcstDate + fcstTime → LocalDateTime 변환
                String dateTimeStr = item.getFcstDate() + "T" + item.getFcstTime().substring(0, 2) + ":00:00";
                LocalDateTime measureDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                TbEnv env = new TbEnv();
                env.setSensorId(sensorId);
                env.setSaltPondId(saltPondId);
                env.setSensorType(BigDecimal.valueOf(sensorTypeCode));
                env.setSensorVal(sensorVal);
                env.setMeasureDatetime(measureDateTime);
                env.setCreateDate(LocalDateTime.now());

                tbEnvRepository.save(env);

            } catch (Exception e) {
                System.err.println("기상 데이터 저장 실패: " + item + " / " + e.getMessage());
            }
        }
    }
}
