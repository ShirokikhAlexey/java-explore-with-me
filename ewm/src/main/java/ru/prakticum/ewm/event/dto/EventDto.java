package ru.prakticum.ewm.event.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.prakticum.ewm.category.dto.CategoryDto;
import ru.prakticum.ewm.event.model.Location;
import ru.prakticum.ewm.event.model.Status;
import ru.prakticum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class EventDto {
    private Integer id;
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private Status state;
    private String title;
    private Integer views;

    public EventDto(Integer id, String annotation, CategoryDto category, Integer confirmedRequests,
                    LocalDateTime createdOn, String description, LocalDateTime eventDate, UserShortDto initiator,
                    Location location, Boolean paid, Integer participantLimit, LocalDateTime publishedOn,
                    Boolean requestModeration, Status state, String title, Integer views) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.views = views;
    }

    public EventDto(Integer id, String annotation, Integer confirmedRequests,
                    LocalDateTime createdOn, String description, LocalDateTime eventDate,
                    Location location, Boolean paid, Integer participantLimit, LocalDateTime publishedOn,
                    Boolean requestModeration, Status state, String title, Integer views) {
        this.id = id;
        this.annotation = annotation;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.views = views;
    }
}
