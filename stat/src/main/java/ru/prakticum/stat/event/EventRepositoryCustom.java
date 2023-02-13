package ru.prakticum.stat.event;

import ru.prakticum.stat.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {
    List<Event> getStatisticsWithUris(LocalDateTime start, LocalDateTime end, List<String> uris,
                                      Boolean distinct);
}
