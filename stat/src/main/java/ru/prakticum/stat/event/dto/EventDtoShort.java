package ru.prakticum.stat.event.dto;

import lombok.Data;

@Data
public class EventDtoShort implements Comparable<EventDtoShort> {
    private String app;
    private String uri;
    private Integer hits;

    EventDtoShort(String app, String uri, Integer hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }

    @Override
    public int compareTo(EventDtoShort another) {
        if (hits == null || another.getHits() == null) {
            return 0;
        }
        return hits.compareTo(another.getHits());
    }
}
