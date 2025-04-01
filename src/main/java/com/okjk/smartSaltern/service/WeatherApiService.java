package com.okjk.smartSaltern.service;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okjk.smartSaltern.entity.TbEnv;
import com.okjk.smartSaltern.repository.TbEnvRepository;

@Service
public class WeatherApiService {

    @Value("${weather.api.key}")
    private String encodedKey;

    private final RestTemplate restTemplate;

    @Autowired
    private TbEnvRepository tbEnvRepository;

    public WeatherApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getVilageFcst(String baseDate, String baseTime, int nx, int ny) {
        try {
            String url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
                    + "?serviceKey=" + encodedKey
                    + "&pageNo=1"
                    + "&numOfRows=1000"
                    + "&dataType=JSON"
                    + "&base_date=" + baseDate
                    + "&base_time=" + baseTime
                    + "&nx=" + nx
                    + "&ny=" + ny;

            System.out.println("최종 호출 URL: " + url);

            URI uri = new URI(url);
            return restTemplate.getForObject(uri, String.class);

        } catch (Exception e) {
            e.printStackTrace();
            return "API 호출 실패: " + e.getMessage();
        }
    }

    public void saveToTbEnv(String json, Long spIdx) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> allowedCategories = List.of("TMP", "REH", "WSD", "PCP", "SUN");

        try {
            JsonNode items = mapper.readTree(json)
                .path("response").path("body").path("items").path("item");

            for (JsonNode item : items) {
                String category = item.path("category").asText();
                if (!allowedCategories.contains(category)) continue;

                String fcstDate = item.path("fcstDate").asText();
                String fcstTime = item.path("fcstTime").asText();
                String fcstValue = item.path("fcstValue").asText();

                if ("강수없음".equals(fcstValue)) {
                    fcstValue = "0";
                } else {
                    fcstValue = fcstValue.replaceAll("[^\\d.]+", "");
                }

                // ✅ 날짜 파싱 포맷 맞춤
                String dateTimeStr = fcstDate + fcstTime.substring(0, 2) + "0000";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime measureDateTime = LocalDateTime.parse(dateTimeStr, formatter);

                TbEnv env = new TbEnv();
                env.setSensorId(getSensorId(category));
                env.setSensorType(getSensorType(category));
                env.setSensorVal(new BigDecimal(fcstValue));
                env.setMeasureDatetime(measureDateTime);
                env.setSaltPondId(spIdx);
                env.setCreateDate(LocalDateTime.now());

                tbEnvRepository.save(env);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("기상 정보 저장 중 오류 발생: " + e.getMessage());
        }
    }

    private Long getSensorId(String category) {
        return switch (category) {
            case "TMP" -> 101L;
            case "REH" -> 102L;
            case "WSD" -> 103L;
            case "PCP" -> 104L;
            case "SUN" -> 105L;
            default -> 999L;
        };
    }

    private BigDecimal getSensorType(String category) {
    	return switch (category) {
        case "TMP" -> BigDecimal.valueOf(1);
        case "REH" -> BigDecimal.valueOf(2);
        case "WSD" -> BigDecimal.valueOf(3);
        case "PCP" -> BigDecimal.valueOf(4);
        case "SUN" -> BigDecimal.valueOf(5);
        default -> BigDecimal.ZERO;
    };
    }

    public Map<String, String> getLatestBaseDateTime() {
        LocalDateTime now = LocalDateTime.now();

        int[] baseTimes = {2300, 2000, 1700, 1400, 1100, 800, 500, 200};
        String baseTime = "0200";
        String baseDate;

        for (int time : baseTimes) {
            int hour = time / 100;
            LocalDateTime baseDateTime = now.withHour(hour).withMinute(0).withSecond(0).withNano(0);
            if (now.isAfter(baseDateTime.plusMinutes(30))) {
                baseTime = String.format("%04d", time);
                break;
            }
        }

        if (baseTime.equals("2300") && now.getHour() < 23) {
            baseDate = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        } else {
            baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return Map.of("baseDate", baseDate, "baseTime", baseTime);
    }
}
