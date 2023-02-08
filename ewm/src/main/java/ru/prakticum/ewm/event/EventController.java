package ru.prakticum.ewm.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.EventShortDto;
import ru.prakticum.ewm.util.StatClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/events")
public class EventController {
    private final EventService eventService;
    private final StatClient statClient;

    @Autowired
    public EventController(EventService eventService, StatClient statClient) {
        this.eventService = eventService;
        this.statClient = statClient;
    }

    @GetMapping
    public List<EventShortDto> getState(@RequestParam(required = false) String text,
                                        @RequestParam(required = false) List<Integer> categories,
                                        @RequestParam(required = false) Boolean paid,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(required = false) LocalDateTime rangeStart,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(required = false) LocalDateTime rangeEnd,
                                        @RequestParam(required = false) Boolean onlyAvailable,
                                        @RequestParam(required = false) String sort,
                                        @RequestParam(defaultValue = "1", required = false) Integer from,
                                        @RequestParam(defaultValue = "10", required = false) Integer size,
                                        HttpServletRequest request) {
        if (from < 0 || size < 0) {
            throw new ValidationException();
        }
        //statClient.saveRequest("/events", request.getRemoteAddr(), LocalDateTime.now());
        return eventService.searchEventsShort(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
    }

    @GetMapping(value = "/{eventId}")
    public EventDto get(@PathVariable int eventId, HttpServletRequest request) {
        //statClient.saveRequest("/events/" + eventId, request.getRemoteAddr(), LocalDateTime.now());
        return eventService.getShort(eventId);
    }
}
