package ru.prakticum.stat.event;

import ru.prakticum.stat.event.dto.EventDto;
import ru.prakticum.stat.exception.InvalidEventException;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDto create(EventDto eventDto) throws InvalidEventException;

    List<EventDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
