package ru.prakticum.stat.event;

import org.springframework.stereotype.Service;
import ru.prakticum.stat.event.dto.EventDto;
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
    public List<EventDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<Event> events;
        List<EventDto> eventDtos = new ArrayList<>();
        HashMap<String, HashMap<String, Integer>> counter = new HashMap<>();

        if (uris.isEmpty() && unique) {
            events = repository.getStatisticsUnique(start, end);
        } else if (uris.isEmpty()) {
            events = repository.getStatistics(start, end);
        } else if (unique) {
            events = repository.getStatisticsWithUrisUnique(start, end, uris);
        } else {
            events = repository.getStatisticsWithUris(start, end, uris);
        }
        for (Event event : events) {
            if (counter.containsKey(event.getApp())) {
                if (!counter.containsKey(event.getUri())) {
                    counter.get(event.getApp()).put(event.getUri(), 0);
                }
            } else {
                counter.put(event.getApp(), new HashMap<>());
                counter.get(event.getApp()).put(event.getUri(), 0);
            }
            counter.get(event.getApp()).put(event.getUri(), counter.get(event.getApp()).get(event.getUri()) + 1);
        }
        for (Event event : events) {
            eventDtos.add(EventMapper.toDto(event, counter.get(event.getApp()).get(event.getUri())));
        }
        return eventDtos;
    }
}
