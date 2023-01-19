package ru.prakticum.stat.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.prakticum.stat.exception.InvalidEventException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class EventDto {
    private Integer id;
    private String app;
    private String uri;
    private String ip;
    private Integer hits;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    EventDto(String app, String uri, String ip, LocalDateTime timestamp) {
        this.app = app;
        this.uri = this.getUrlWithoutParameters(uri).strip();
        this.ip = ip;
        this.timestamp = timestamp;
    }

    public EventDto(Integer id, String app, String uri, String ip, LocalDateTime timestamp, Integer hits) {
        this.id = id;
        this.app = app;
        this.uri = this.getUrlWithoutParameters(uri).strip();
        this.ip = ip;
        this.timestamp = timestamp;
        this.hits = hits;
    }

    private String getUrlWithoutParameters(String url) {
        try {
            URI uri = new URI(url);
            String newURI = new URI(uri.getScheme(),
                    null,
                    uri.getPath(),
                    null, // Ignore the query part of the input url
                    null).toString();

            return newURI.substring(0, newURI.lastIndexOf("/"));
        } catch (URISyntaxException e) {
            throw new InvalidEventException();
        }
    }
}
