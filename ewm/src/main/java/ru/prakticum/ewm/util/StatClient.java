package ru.prakticum.ewm.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class StatClient {
    private final RestTemplate rest;

    private final String url;

    public StatClient(@Value("${ewm-stat.url:default}") String statisticUrl) {
        url = statisticUrl + "/hit";
        rest = new RestTemplate();
    }

    public void saveRequest(String path, String ip, LocalDateTime timestamp) {
        String app = "ewm-main-service";
        StatDto body = new StatDto(path, ip, timestamp, app);
        HttpEntity<StatDto> requestEntity = new HttpEntity<>(body, defaultHeaders());

        try {
            rest.exchange(url, HttpMethod.POST, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            log.error("While logging statistic data an error occurred");
        }
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

}
