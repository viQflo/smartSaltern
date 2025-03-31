package com.okjk.smartSaltern.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SERVICE_KEY = "G8KYlG/RTHhwT0t+1WnkHwj17XRZEgGQOMyle3tku1CfKwOx5XJ3OrRQfCgmM7uV1vetAdHP1sSZb66W40qGGQ==";

    private static final String API_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

    public JsonNode getWeatherData(double nx, double ny) {
        try {
            // 서버 시간 로깅
            System.out.println("서버 현재 시간: " + LocalDateTime.now());

            String baseDate = getBaseDate();
            String baseTime = getBaseTime();

            System.out.println("📅 기준 날짜: " + baseDate + ", 기준 시간: " + baseTime);

            String decodedKey = URLDecoder.decode(SERVICE_KEY, StandardCharsets.UTF_8);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("serviceKey", decodedKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", (int) nx)
                .queryParam("ny", (int) ny);

            String url = builder.toUriString();
            System.out.println("🔗 호출 URL: " + url);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
            );

            String responseBody = response.getBody();

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("❌ HTTP 에러 발생: " + response.getStatusCode());
            }

            if (responseBody != null && responseBody.trim().startsWith("<")) {
                System.out.println("XML 오류 응답: " + responseBody);
                throw new RuntimeException("❌ API 호출 실패: XML 응답 수신됨 - " + responseBody);
            }

            JsonNode root = objectMapper.readTree(responseBody);

            JsonNode header = root.path("response").path("header");
            if (!"00".equals(header.path("resultCode").asText())) {
                throw new RuntimeException("API 에러: " + header.path("resultMsg").asText());
            }

            System.out.println("✅ JSON 응답 처리 완료");

            return root;

        } catch (Exception e) {
            System.out.println("❌ 예외 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("❌ 날씨 API 호출 중 오류 발생: " + e.getMessage(), e);
        }
    }

    private String getBaseTime() {
        String[] allowedTimes = {"0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300"};

        LocalTime now = LocalTime.now().minusMinutes(45);

        for (int i = allowedTimes.length - 1; i >= 0; i--) {
            LocalTime allowed = LocalTime.parse(allowedTimes[i], DateTimeFormatter.ofPattern("HHmm"));
            if (!now.isBefore(allowed)) {
                return allowedTimes[i];
            }
        }

        return "2300";
    }

    private String getBaseDate() {
        LocalTime now = LocalTime.now();
        String baseTime = getBaseTime();

        if (now.isBefore(LocalTime.of(2, 45)) && "2300".equals(baseTime)) {
            return LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}