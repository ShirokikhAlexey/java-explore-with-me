package ru.prakticum.ewm.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.RequestDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{userId}/events")
    public List<EventDto> get(@PathVariable int userId,
                              @RequestParam(defaultValue = "1", required = false) Integer from,
                              @RequestParam(defaultValue = "10", required = false) Integer size) {
        return userService.getUserEvents(userId, from, size);
    }

    @PatchMapping(value = "/{userId}/events")
    public EventDto update(@PathVariable int userId, @RequestBody EventDto eventDto) {
        return userService.updateUserEvent(userId, eventDto);
    }

    @PostMapping(value = "/{userId}/events")
    public EventDto save(@PathVariable int userId, @RequestBody EventDto eventDto) {
        return userService.saveUserEvent(userId, eventDto);
    }

    @GetMapping(value = "/{userId}/events/{eventId}")
    public EventDto get(@PathVariable int userId, @PathVariable int eventId) {
        return userService.getUserEvent(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}")
    public EventDto cancel(@PathVariable int userId, @PathVariable int eventId) {
        return userService.cancelUserEvent(userId, eventId);
    }

    @GetMapping(value = "/{userId}/events/{eventId}/requests")
    public List<RequestDto> getRequests(@PathVariable int userId, @PathVariable int eventId) {
        return userService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests/{requestId}/confirm")
    public RequestDto approveRequest(@PathVariable int userId, @PathVariable int eventId, @PathVariable int requestId) {
        return userService.approveRequest(userId, eventId, requestId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests/{requestId}/reject")
    public RequestDto rejectRequest(@PathVariable int userId, @PathVariable int eventId, @PathVariable int requestId) {
        return userService.rejectRequest(userId, eventId, requestId);
    }

    @GetMapping(value = "/{userId}/requests")
    public List<RequestDto> getRequests(@PathVariable int userId) {
        return userService.getUserRequests(userId);
    }

    @PostMapping(value = "/{userId}/requests")
    public RequestDto addUserRequest(@PathVariable int userId, @RequestParam Integer eventId) {
        return userService.addUserRequest(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/requests/{requestId}")
    public RequestDto cancelUserRequest(@PathVariable int userId, @PathVariable Integer requestId) {
        return userService.cancelUserRequest(userId, requestId);
    }
}
