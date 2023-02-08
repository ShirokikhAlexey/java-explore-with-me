package ru.prakticum.ewm.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.PatchRequestDto;
import ru.prakticum.ewm.event.dto.RequestDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}/events")
    public List<EventDto> get(@PathVariable int userId,
                              @RequestParam(defaultValue = "1", required = false) Integer from,
                              @RequestParam(defaultValue = "10", required = false) Integer size) {
        return userService.getUserEvents(userId, from, size);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}")
    public EventDto update(@PathVariable int userId, @PathVariable int eventId, @RequestBody EventDto eventDto) {
        eventDto.setId(eventId);
        return userService.updateUserEvent(userId, eventDto);
    }

    @PostMapping(value = "/{userId}/events")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventDto save(@PathVariable int userId, @RequestBody EventDto eventDto) {
        System.out.println(eventDto.toString());
        return userService.saveUserEvent(userId, eventDto);
    }

    @GetMapping(value = "/{userId}/events/{eventId}")
    public EventDto get(@PathVariable int userId, @PathVariable int eventId) {
        return userService.getUserEvent(userId, eventId);
    }

    @GetMapping(value = "/{userId}/events/{eventId}/requests")
    public List<RequestDto> getRequests(@PathVariable int userId, @PathVariable int eventId) {
        return userService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests")
    public HashMap<String, List<RequestDto>> approveRequest(@PathVariable int userId, @PathVariable int eventId,
                                                            @RequestBody PatchRequestDto requestDto) {
        HashMap<String, List<RequestDto>> response = new HashMap<>();
        response.put("confirmedRequests", new ArrayList<>());
        response.put("rejectedRequests", new ArrayList<>());
        for (Integer requestId : requestDto.getRequestIds()) {
            switch (requestDto.getStatus()) {
                case CONFIRMED:
                    response.get("confirmedRequests").add(userService.approveRequest(userId, eventId, requestId));
                    break;
                case REJECTED:
                    response.get("rejectedRequests").add(userService.rejectRequest(userId, eventId, requestId));
                    break;
            }
        }
        return response;
    }

    @GetMapping(value = "/{userId}/requests")
    public List<RequestDto> getRequests(@PathVariable int userId) {
        return userService.getUserRequests(userId);
    }

    @PostMapping(value = "/{userId}/requests")
    @ResponseStatus(value = HttpStatus.CREATED)
    public RequestDto addUserRequest(@PathVariable int userId, @RequestParam Integer eventId) {
        return userService.addUserRequest(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelUserRequest(@PathVariable int userId, @PathVariable Integer requestId) {
        return userService.cancelUserRequest(userId, requestId);
    }
}
