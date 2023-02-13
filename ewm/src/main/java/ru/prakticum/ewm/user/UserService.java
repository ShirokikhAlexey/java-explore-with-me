package ru.prakticum.ewm.user;

import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.RequestDto;

import java.util.List;

public interface UserService {
    List<EventDto> getUserEvents(Integer userId, Integer from, Integer size);

    EventDto updateUserEvent(Integer userId, EventDto eventDto);

    EventDto saveUserEvent(Integer userId, EventDto eventDto);

    EventDto getUserEvent(Integer userId, Integer eventId);

    EventDto cancelUserEvent(Integer userId, Integer eventId);

    List<RequestDto> getUserEventRequests(Integer userId, Integer eventId);

    RequestDto approveRequest(Integer userId, Integer eventId, Integer requestId);

    RequestDto rejectRequest(Integer userId, Integer eventId, Integer requestId);

    List<RequestDto> getUserRequests(Integer userId);

    RequestDto addUserRequest(Integer userId, Integer eventId);

    RequestDto cancelUserRequest(Integer userId, Integer requestId);
}
