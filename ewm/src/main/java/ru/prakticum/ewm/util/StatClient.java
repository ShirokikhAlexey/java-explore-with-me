package ru.prakticum.ewm.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class StatClient {
    private String app = "ewm-main-service";
    protected final RestTemplate rest;

    @Autowired
    public StatClient(@Value("${ewm-stat.url}") String statisticUrl, RestTemplateBuilder builder) {
        rest = builder.uriTemplateHandler(new DefaultUriBuilderFactory(statisticUrl + "/hit"))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public void saveRequest(String path, String ip, LocalDateTime timestamp) {
        StatDto body = new StatDto(path, ip, timestamp, app);
        HttpEntity<StatDto> requestEntity = new HttpEntity<>(body, defaultHeaders());

        try {
            rest.exchange("", HttpMethod.POST, requestEntity, Object.class);
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
