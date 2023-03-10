package ru.prakticum.ewm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.prakticum.ewm.event.EventRepository;
import ru.prakticum.ewm.event.RequestRepository;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.EventMapper;
import ru.prakticum.ewm.event.dto.RequestDto;
import ru.prakticum.ewm.event.model.Event;
import ru.prakticum.ewm.event.model.Request;
import ru.prakticum.ewm.event.model.RequestStatus;
import ru.prakticum.ewm.event.model.Status;
import ru.prakticum.ewm.exception.ConflictException;
import ru.prakticum.ewm.exception.InvalidEventException;
import ru.prakticum.ewm.exception.NotFoundException;
import ru.prakticum.ewm.location.LocationRepository;
import ru.prakticum.ewm.location.model.Location;
import ru.prakticum.ewm.user.dto.UserDtoMapper;
import ru.prakticum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<EventDto> getUserEvents(Integer userId, Integer from, Integer size) {
        List<EventDto> result = new ArrayList<>();
        List<Event> events = eventRepository.getUserEvents(userId, PageRequest.of(from / size, size));
        for (Event e : events) {
            Long confirmed = eventRepository.getApprovedCount(e.getId());
            result.add(EventMapper.toDto(e, Math.toIntExact(confirmed)));
        }
        return result;
    }

    @Override
    public EventDto updateUserEvent(Integer userId, EventDto eventDto) {
        Optional<Event> eventOptional = eventRepository.findById(eventDto.getId());
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        Event event = eventOptional.get();
        if (!Objects.equals(event.getInitiator().getId(), userId) ||
                event.getState() != Status.PENDING && event.getState() != Status.CANCELED ||
                event.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new ConflictException("Invalid event");
        }

        if (Objects.equals(eventDto.getStateAction(), "CANCEL_REVIEW")) {
            return cancelUserEvent(userId, event.getId());
        }

        eventDto.setState(Status.PENDING);
        Event event1 = EventMapper.updateEvent(event, eventDto);
        return EventMapper.toDto(eventRepository.save(event1));
    }

    @Override
    public EventDto saveUserEvent(Integer userId, EventDto eventDto) {
        Optional<User> initiatorOptional = userRepository.findById(userId);
        if (initiatorOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        User initiator = initiatorOptional.get();
        if (eventDto.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new ConflictException("Invalid event");
        }

        List<Location> locations = locationRepository.getByCoordinates(eventDto.getLocation().getLat(), eventDto.getLocation().getLat());
        if (locations.isEmpty()) {
            Location saved = locationRepository.save(eventDto.getLocation());
            eventDto.getLocation().setId(saved.getId());
        } else {
            eventDto.getLocation().setId(locations.get(0).getId());
        }

        eventDto.setInitiator(UserDtoMapper.toDtoShort(initiator));
        eventDto.setState(Status.PENDING);
        return EventMapper.toDto(eventRepository.save(EventMapper.fromDto(eventDto)));
    }

    @Override
    public EventDto getUserEvent(Integer userId, Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException("Not found");
        }
        return EventMapper.toDto(eventOptional.get());
    }

    @Override
    public EventDto cancelUserEvent(Integer userId, Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Event not found");
        }
        if (eventOptional.get().getState() != Status.PENDING) {
            throw new InvalidEventException("Invalid event status " + eventOptional.get().getState().name());
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException("Not found");
        }
        Event event = eventOptional.get();
        event.setState(Status.CANCELED);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public List<RequestDto> getUserEventRequests(Integer userId, Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException("Not found");
        }
        List<RequestDto> response = new ArrayList<>();
        for (Request r : eventOptional.get().getRequests()) {
            response.add(EventMapper.requestToDto(r));
        }
        return response;
    }

    @Override
    public RequestDto approveRequest(Integer userId, Integer eventId, Integer requestId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException("Not found");
        }
        Long currentApproved = eventRepository.getApprovedCount(eventId);
        if (Objects.equals(Math.toIntExact(currentApproved), eventOptional.get().getParticipantLimit())) {
            throw new ConflictException("Invalid event");
        }

        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Request request = requestOptional.get();
        request.setStatus(RequestStatus.CONFIRMED);
        requestRepository.save(request);

        if (Objects.equals(Math.toIntExact(currentApproved) + 1, eventOptional.get().getParticipantLimit())) {
            for (Request r : requestRepository.getEventRequestsPending(eventId)) {
                r.setStatus(RequestStatus.REJECTED);
                requestRepository.save(r);
            }
        }
        return EventMapper.requestToDto(request);
    }

    @Override
    public RequestDto rejectRequest(Integer userId, Integer eventId, Integer requestId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException("Not found");
        }

        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Request request = requestOptional.get();
        if (request.getStatus().equals(RequestStatus.CONFIRMED) || request.getStatus().equals(RequestStatus.REJECTED)) {
            throw new ConflictException("Status confirmed");
        }
        request.setStatus(RequestStatus.REJECTED);

        return EventMapper.requestToDto(request);
    }

    @Override
    public List<RequestDto> getUserRequests(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        List<RequestDto> response = new ArrayList<>();
        for (Request r : requestRepository.getUserRequests(userId)) {
            response.add(EventMapper.requestToDto(r));
        }
        return response;
    }

    @Override
    public RequestDto addUserRequest(Integer userId, Integer eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User Not found");
        }
        User user = userOptional.get();
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new ConflictException("Event not found");
        }
        Event event = eventOptional.get();
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException("Request from initiator");
        }

        Long currentApproved = eventRepository.getApprovedCount(eventId);
        if (Objects.equals(Math.toIntExact(currentApproved), event.getParticipantLimit()) ||
                event.getState() != Status.PUBLISHED) {
            throw new ConflictException("Invalid event");
        }

        if (!event.getRequestModeration()) {
            return EventMapper.requestToDto(requestRepository.save(new Request(user, event, RequestStatus.CONFIRMED)));
        }
        return EventMapper.requestToDto(requestRepository.save(new Request(user, event, RequestStatus.PENDING)));
    }

    @Override
    public RequestDto cancelUserRequest(Integer userId, Integer requestId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isEmpty() || !Objects.equals(requestOptional.get().getUser().getId(), userId)) {
            throw new NotFoundException("Not found");
        }
        Request request = requestOptional.get();
        request.setStatus(RequestStatus.CANCELED);
        return EventMapper.requestToDto(requestRepository.save(request));

    }
}
