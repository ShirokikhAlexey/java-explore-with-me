package ru.prakticum.ewm.event;

import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.EventShortDto;
import ru.prakticum.ewm.exception.InvalidEventException;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDto create(EventDto eventDto) throws InvalidEventException;

    EventDto create(EventShortDto eventDto) throws InvalidEventException;

    List<EventShortDto> searchEventsShort(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                          Integer size);

    EventDto getShort(Integer eventId);
}
