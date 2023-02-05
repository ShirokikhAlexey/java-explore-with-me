package ru.prakticum.stat.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.prakticum.stat.event.dto.EventDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping()
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/stats")
    public List<EventDto> search(@RequestParam
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                         LocalDateTime start,
                                 @RequestParam
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                         LocalDateTime end,
                                 @RequestParam(defaultValue = "", required = false) List<String> uris,
                                 @RequestParam(defaultValue = "false", required = false) boolean unique) {
        List<String> test = new ArrayList<>();
        for (String uri : uris) {
            test.add(uri.strip());
        }
        return eventService.getStatistics(start, end, test, unique);
    }

    @PostMapping("/hit")
    public EventDto create(@RequestBody EventDto eventDto) {
        return eventService.create(eventDto);
    }
}
