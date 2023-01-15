package ru.prakticum.ewm.user;

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
import ru.prakticum.ewm.exception.InvalidEventException;
import ru.prakticum.ewm.exception.NotFoundException;
import ru.prakticum.ewm.user.dto.UserDtoMapper;
import ru.prakticum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository,
                           RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<EventDto> getUserEvents(Integer userId, Integer from, Integer size) {
        List<EventDto> result = new ArrayList<>();
        List<Event> events = eventRepository.getUserEvents(userId,  PageRequest.of(from / size, size));
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
            throw new InvalidEventException();
        }
        Event event = eventOptional.get();
        if (!Objects.equals(event.getInitiator().getId(), userId) ||
                event.getState() != Status.PENDING && event.getState() != Status.CANCELED ||
                event.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new InvalidEventException();
        }

        eventDto.setState(Status.PENDING);
        return EventMapper.toDto(eventRepository.save(EventMapper.updateEvent(event, eventDto)));
    }

    @Override
    public EventDto saveUserEvent(Integer userId, EventDto eventDto) {
        Optional<User> initiatorOptional = userRepository.findById(userId);
        if (initiatorOptional.isEmpty()) {
            throw new NotFoundException();
        }
        User initiator = initiatorOptional.get();
        if (eventDto.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new InvalidEventException();
        }
        eventDto.setInitiator(UserDtoMapper.toDtoShort(initiator));
        eventDto.setState(Status.PENDING);
        return EventMapper.toDto(eventRepository.save(EventMapper.fromDto(eventDto)));
    }

    @Override
    public EventDto getUserEvent(Integer userId, Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException();
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException();
        }
        return EventMapper.toDto(eventOptional.get());
    }

    @Override
    public EventDto cancelUserEvent(Integer userId, Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty() || eventOptional.get().getState() != Status.PUBLISHED) {
            throw new InvalidEventException();
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException();
        }
        Event event = eventOptional.get();
        event.setState(Status.CANCELED);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public List<RequestDto> getUserEventRequests(Integer userId, Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException();
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException();
        }
        List<RequestDto> response = new ArrayList<>();
        for(Request r : eventOptional.get().getRequests()) {
            response.add(EventMapper.requestToDto(r));
        }
        return response;
    }

    @Override
    public RequestDto approveRequest(Integer userId, Integer eventId, Integer requestId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException();
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException();
        }
        Long currentApproved = eventRepository.getApprovedCount(eventId);
        if (Objects.equals(Math.toIntExact(currentApproved), eventOptional.get().getParticipantLimit())) {
            throw new InvalidEventException();
        }

        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isEmpty()) {
            throw new NotFoundException();
        }
        Request request = requestOptional.get();
        request.setStatus(RequestStatus.APPROVED);

        if (Objects.equals(Math.toIntExact(currentApproved) + 1, eventOptional.get().getParticipantLimit())) {
            for (Request r: requestRepository.getEventRequestsPending(eventId)) {
                r.setStatus(RequestStatus.DECLINED);
                requestRepository.save(r);
            }
        }
        return EventMapper.requestToDto(request);
    }

    @Override
    public RequestDto rejectRequest(Integer userId, Integer eventId, Integer requestId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException();
        }
        if (!Objects.equals(eventOptional.get().getInitiator().getId(), userId)) {
            throw new NotFoundException();
        }

        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isEmpty()) {
            throw new NotFoundException();
        }
        Request request = requestOptional.get();
        request.setStatus(RequestStatus.DECLINED);

        return EventMapper.requestToDto(request);
    }

    @Override
    public List<RequestDto> getUserRequests(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException();
        }
        List<RequestDto> response = new ArrayList<>();
        for(Request r : requestRepository.getUserRequests(userId)) {
            response.add(EventMapper.requestToDto(r));
        }
        return response;
    }

    @Override
    public RequestDto addUserRequest(Integer userId, Integer eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException();
        }
        User user = userOptional.get();
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException();
        }
        Event event = eventOptional.get();
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new InvalidEventException();
        }
        if (!event.getRequestModeration()) {
            return EventMapper.requestToDto(requestRepository.save(new Request(user, event, RequestStatus.APPROVED)));
        }

        Long currentApproved = eventRepository.getApprovedCount(eventId);
        if (Objects.equals(Math.toIntExact(currentApproved), event.getParticipantLimit()) ||
                event.getState() != Status.PUBLISHED) {
            throw new InvalidEventException();
        }
        return EventMapper.requestToDto(requestRepository.save(new Request(user, event, RequestStatus.PENDING)));
    }

    @Override
    public RequestDto cancelUserRequest(Integer userId, Integer requestId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException();
        }
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isEmpty() || !Objects.equals(requestOptional.get().getUser().getId(), userId)) {
            throw new NotFoundException();
        }
        Request request = requestOptional.get();
        request.setStatus(RequestStatus.CANCELED);
        return EventMapper.requestToDto(requestRepository.save(request));

    }
}
