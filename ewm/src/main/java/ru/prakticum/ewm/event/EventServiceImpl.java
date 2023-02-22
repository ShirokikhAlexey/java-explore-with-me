package ru.prakticum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.EventMapper;
import ru.prakticum.ewm.event.dto.EventShortDto;
import ru.prakticum.ewm.event.model.Event;
import ru.prakticum.ewm.event.model.Status;
import ru.prakticum.ewm.exception.EventNotFoundException;
import ru.prakticum.ewm.exception.InvalidEventException;
import ru.prakticum.ewm.location.LocationRepository;
import ru.prakticum.ewm.location.model.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    private final LocationRepository locationRepository;

    @Override
    public EventDto create(EventShortDto eventDto) throws InvalidEventException {
        return EventMapper.toDto(repository.save(EventMapper.fromDtoShort(eventDto)));
    }

    @Override
    public EventDto create(EventDto eventDto) throws InvalidEventException {
        return EventMapper.toDto(repository.save(EventMapper.fromDto(eventDto)));
    }

    @Override
    public List<EventShortDto> searchEventsShort(String text, List<Integer> categories, Boolean paid,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                 String sort, Integer from, Integer size) {
        List<EventShortDto> result = new ArrayList<>();
        List<Event> events = repository.search(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
        for (Event e : events) {
            result.add(EventMapper.toDtoShort(e));
        }
        return result;
    }

    @Override
    public EventDto getShort(Integer eventId) {
        Optional<Event> event = repository.findById(eventId);
        if (event.isEmpty()) {
            throw new EventNotFoundException("Not found");
        }
        if (event.get().getState() != Status.PUBLISHED) {
            throw new InvalidEventException("Invalid event");
        }
        Long confirmed = repository.getApprovedCount(eventId);
        return EventMapper.toDto(event.get(), Math.toIntExact(confirmed));
    }
}
