package ru.prakticum.stat.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDtoShort implements Comparable<EventDtoShort> {
    private String app;
    private String uri;
    private Integer hits;

    @Override
    public int compareTo(EventDtoShort another) {
        if (hits == null || another.getHits() == null) {
            return 0;
        }
        return hits.compareTo(another.getHits());
    }
}
