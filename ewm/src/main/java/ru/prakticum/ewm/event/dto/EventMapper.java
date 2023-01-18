package ru.prakticum.ewm.event.dto;

import ru.prakticum.ewm.category.dto.CategoryDtoMapper;
import ru.prakticum.ewm.category.model.Category;
import ru.prakticum.ewm.event.model.Event;
import ru.prakticum.ewm.event.model.Location;
import ru.prakticum.ewm.event.model.Request;
import ru.prakticum.ewm.user.dto.UserDtoMapper;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    public static EventDto toDto(Event event, Integer confirmedRequests) {
        EventDto eventDto = new EventDto(event.getId(), event.getAnnotation(),
                confirmedRequests, event.getCreatedOn(), event.getDescription(), event.getEventDate(),
                event.getPaid(), event.getParticipantLimit(), event.getPublishedOn(), event.getRequestModeration(),
                event.getState(), event.getTitle(), event.getViews());
        if (event.getCategories() != null && !event.getCategories().isEmpty()) {
            eventDto.setCategory(CategoryDtoMapper.toDto(event.getCategories().get(0)));
        }
        if (event.getLocations() != null && !event.getLocations().isEmpty()) {
            eventDto.setLocation(event.getLocations().get(0));
        }
        eventDto.setInitiator(UserDtoMapper.toDtoShort(event.getInitiator()));
        return eventDto;
    }

    public static EventDto toDto(Event event) {
        return EventMapper.toDto(event, null);
    }

    public static Event fromDtoShort(EventShortDto eventDto) {
        return new Event(eventDto.getId(), eventDto.getAnnotation(), null,
                List.of(CategoryDtoMapper.fromDto(eventDto.getCategory())),
                eventDto.getEventDate(), UserDtoMapper.fromDto(eventDto.getInitiator()),
                eventDto.getPaid(), eventDto.getTitle(),
                eventDto.getViews());
    }

    public static Event fromDto(EventDto eventDto) {
        return new Event(eventDto.getId(), eventDto.getAnnotation(), eventDto.getDescription(),
                List.of(CategoryDtoMapper.fromDto(eventDto.getCategory())),
                eventDto.getEventDate(), UserDtoMapper.fromDto(eventDto.getInitiator()),
                eventDto.getPaid(), eventDto.getTitle(),
                eventDto.getViews(), eventDto.getCreatedOn(), eventDto.getState(), eventDto.getParticipantLimit(), eventDto.getLocation(),
                eventDto.getRequestModeration());
    }

    public static EventShortDto toDtoShort(Event event, Integer confirmedRequests) {
        return new EventShortDto(event.getId(), event.getAnnotation(),
                CategoryDtoMapper.toDto(event.getCategories().get(0)), confirmedRequests, event.getEventDate(),
                UserDtoMapper.toDtoShort(event.getInitiator()), event.getPaid(), event.getTitle(), event.getViews());
    }

    public static EventShortDto toDtoShort(Event event) {
        return EventMapper.toDtoShort(event, null);
    }

    public static Event updateEvent(Event event, EventDto eventDto) {
        if(eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if(eventDto.getCategory() != null) {
            List<Category> categories = new ArrayList<>();
            categories.add(CategoryDtoMapper.fromDto(eventDto.getCategory()));
            event.setCategories(categories);
        }
        if(eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if(eventDto.getEventDate() != null) {
            event.setEventDate(eventDto.getEventDate());
        }
        if(eventDto.getInitiator() != null) {
            event.setInitiator(UserDtoMapper.fromDto(eventDto.getInitiator()));
        }
        if(eventDto.getLocation() != null) {
            List<Location> locations = new ArrayList<>();
            locations.add(eventDto.getLocation());
            event.setLocations(locations);
        }
        if(eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if(eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if(eventDto.getState() != null) {
            event.setState(eventDto.getState());
        }
        if(eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }

        return event;
    }

    public static RequestDto requestToDto(Request request) {
        return new RequestDto(request.getId(), request.getEvent().getId(), request.getUser().getId(),
                request.getStatus(), request.getCreatedOn());
    }
}
