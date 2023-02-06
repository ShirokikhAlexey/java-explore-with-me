package ru.prakticum.stat.event.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EventDtoShort {
    private String app;
    private String uri;
    private Integer hits;

    EventDtoShort(String app, String uri, Integer hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
