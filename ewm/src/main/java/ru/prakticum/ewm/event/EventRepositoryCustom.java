package ru.prakticum.ewm.event;

import ru.prakticum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {
    List<Event> search(String text, List<Integer> categories, Boolean paid,
                       LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                       String sort, Integer from, Integer size);

    List<Event> search(String text, List<Integer> users, List<String> states, List<Integer> categories, Boolean paid,
                       LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                       String sort, Integer from, Integer size);

    Long getApprovedCount(Integer id);
}
