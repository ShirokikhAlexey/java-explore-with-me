package ru.prakticum.ewm.event.dto;

import lombok.Data;
import ru.prakticum.ewm.event.model.RequestStatus;

import java.time.LocalDateTime;

@Data
public class RequestDto {
    private Integer id;
    private Integer event;
    private Integer requester;
    private RequestStatus status;
    private LocalDateTime created;

    public RequestDto(Integer id, Integer event, Integer requester, RequestStatus status, LocalDateTime created) {
        this.id = id;
        this.event = event;
        this.requester = requester;
        this.status = status;
        this.created = created;
    }
}
