package ru.prakticum.stat.event.dto;

import ru.prakticum.stat.event.model.Event;

public class EventMapper {
    public static EventDto toDto(Event event, Integer hits) {
        return new EventDto(event.getId(), event.getApp(), event.getUri(), event.getIp(), event.getTimestamp(), hits);
    }

    public static Event fromDto(EventDto eventDto) {
        return new Event(eventDto.getId(), eventDto.getApp(), eventDto.getUri(), eventDto.getIp(), eventDto.getTimestamp());
    }

    public static EventDtoShort toDtoShort(Event event, Integer hits) {
        return new EventDtoShort(event.getApp(), event.getUri(), hits);
    }
}
