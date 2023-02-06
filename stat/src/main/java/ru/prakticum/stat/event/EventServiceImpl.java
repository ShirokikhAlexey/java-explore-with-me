package ru.prakticum.stat.event;

import org.springframework.stereotype.Service;
import ru.prakticum.stat.event.dto.EventDto;
import ru.prakticum.stat.event.dto.EventDtoShort;
import ru.prakticum.stat.event.dto.EventMapper;
import ru.prakticum.stat.event.model.Event;
import ru.prakticum.stat.exception.InvalidEventException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository repository;

    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public EventDto create(EventDto eventDto) throws InvalidEventException {
        return EventMapper.toDto(repository.save(EventMapper.fromDto(eventDto)), null);
    }

    @Override
    public List<EventDtoShort> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<Event> events;
        List<EventDtoShort> eventDtos = new ArrayList<>();
        HashMap<String, Integer> counter = new HashMap<>();
        if (uris.isEmpty() && unique) {
            events = repository.getStatisticsUnique(start, end);
        } else if (uris.isEmpty()) {
            events = repository.getStatistics(start, end);
        } else if (unique) {
            events = repository.getStatisticsWithUris(start, end, uris, true);
        } else {
            events = repository.getStatisticsWithUris(start, end, uris, false);
        }
        for (Event event : events) {
            EventDtoShort dto = EventMapper.toDtoShort(event, null);

            if (!counter.containsKey(dto.getApp() + dto.getUri())) {
                counter.put(dto.getApp() + dto.getUri(), 0);
            }
            counter.put(dto.getApp() + dto.getUri(), counter.get(dto.getApp() + dto.getUri()) + 1);
            eventDtos.add(dto);
        }
        for (EventDtoShort event : eventDtos) {
            event.setHits(counter.get(event.getApp() + event.getUri()));
        }
        return eventDtos;
    }
}
