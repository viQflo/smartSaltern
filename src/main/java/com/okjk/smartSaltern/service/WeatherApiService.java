package com.okjk.smartSaltern.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherApiService {

    @Value("${weather.api.key}")
    private String encodedKey; // ⚠️ 완전 인코딩된 키를 넣는다!

    private final RestTemplate restTemplate;

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

            // 💡 RestTemplate이 더 이상 인코딩하지 않도록 직접 URI 객체 생성
            URI uri = new URI(url);

            return restTemplate.getForObject(uri, String.class);

        } catch (Exception e) {
            e.printStackTrace();
            return "API 호출 실패: " + e.getMessage();
        }
    }
}