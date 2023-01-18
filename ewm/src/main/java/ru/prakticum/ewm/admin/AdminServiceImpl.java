package ru.prakticum.ewm.admin;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.prakticum.ewm.category.CategoryRepository;
import ru.prakticum.ewm.category.dto.CategoryDto;
import ru.prakticum.ewm.category.dto.CategoryDtoMapper;
import ru.prakticum.ewm.category.model.Category;
import ru.prakticum.ewm.compilation.CompilationRepository;
import ru.prakticum.ewm.compilation.dto.CompilationDto;
import ru.prakticum.ewm.compilation.dto.CompilationDtoMapper;
import ru.prakticum.ewm.compilation.dto.CompilationShortDto;
import ru.prakticum.ewm.compilation.model.Compilation;
import ru.prakticum.ewm.event.EventRepository;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.EventMapper;
import ru.prakticum.ewm.event.model.Event;
import ru.prakticum.ewm.event.model.Status;
import ru.prakticum.ewm.exception.InvalidEventException;
import ru.prakticum.ewm.exception.NotFoundException;
import ru.prakticum.ewm.user.UserRepository;
import ru.prakticum.ewm.user.dto.UserDto;
import ru.prakticum.ewm.user.dto.UserDtoMapper;
import ru.prakticum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final CompilationRepository compilationRepository;

    public AdminServiceImpl(UserRepository userRepository, EventRepository eventRepository,
                            CompilationRepository compilationRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.compilationRepository = compilationRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<EventDto> search(List<Integer> users, List<String> states, List<Integer> categories,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        List<Event> events = eventRepository.search(null, users, states, categories, null,
                rangeStart, rangeEnd, null, null, from, size);
        List<EventDto> eventDtos = new ArrayList<>();
        for (Event e : events) {
            Long currentApproved = eventRepository.getApprovedCount(e.getId());
            eventDtos.add(EventMapper.toDto(e, Math.toIntExact(currentApproved)));
        }
        return eventDtos;
    }

    @Override
    public EventDto update(Integer eventId, EventDto eventDto) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        Event event = eventOptional.get();
        return EventMapper.toDto(eventRepository.save(EventMapper.updateEvent(event, eventDto)));
    }

    @Override
    public EventDto publish(Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty() || eventOptional.get().getState() != Status.PENDING) {
            throw new InvalidEventException("Invalid event");
        }
        Event event = eventOptional.get();
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new InvalidEventException("Invalid event");
        }

        event.setState(Status.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public EventDto reject(Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Event not found");
        }
        if (eventOptional.get().getState() != Status.PENDING) {
            throw new InvalidEventException("Event status " + eventOptional.get().getState().name());
        }
        Event event = eventOptional.get();

        event.setState(Status.CANCELED);
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return CategoryDtoMapper.toDto(categoryRepository.save(CategoryDtoMapper.fromDto(categoryDto)));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() == null) {
            throw new InvalidEventException("");
        }
        Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getId());
        if (categoryOptional.isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        return CategoryDtoMapper.toDto(categoryRepository.save(CategoryDtoMapper.updateFromDto(categoryOptional.get(), categoryDto)));
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        Category category = categoryOptional.get();
        if (!category.getEvents().isEmpty()) {
            throw new InvalidEventException("Invalid event");
        }
        categoryRepository.delete(category);
    }

    @Override
    public List<UserDto> searchUsers(List<Integer> users, Integer from, Integer size) {
        List<UserDto> usersDtos = new ArrayList<>();
        if (users != null) {
            List<User> usersObjects = userRepository.searchUsers(users, PageRequest.of(from / size, size));
            for (User u : usersObjects) {
                usersDtos.add(UserDtoMapper.toDto(u));
            }
            return usersDtos;
        }
        List<User> usersObjects = userRepository.searchUsers(PageRequest.of(from / size, size));
        for (User u : usersObjects) {
            usersDtos.add(UserDtoMapper.toDto(u));
        }
        return usersDtos;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        return UserDtoMapper.toDto(userRepository.save(UserDtoMapper.fromDto(userDto)));
    }

    @Override
    public void deleteUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        User user = userOptional.get();
        userRepository.delete(user);
    }

    @Override
    public CompilationDto addCompilation(CompilationShortDto compilationShortDto) {
        List<Event> events = eventRepository.findAllById(compilationShortDto.getEvents());
        Compilation compilation = CompilationDtoMapper.fromDto(new CompilationDto(compilationShortDto.getId(),
                compilationShortDto.getTitle(), compilationShortDto.getPinned()), events);
        return CompilationDtoMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Integer compilationId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compilationId);
        if (compilationOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Compilation compilation = compilationOptional.get();
        compilationRepository.delete(compilation);
    }

    @Override
    public CompilationDto deleteCompilationEvent(Integer compilationId, Integer eventId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compilationId);
        if (compilationOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Compilation compilation = compilationOptional.get();
        List<Event> newEvents = new ArrayList<>();
        for (Event e : compilation.getEvents()) {
            if (!Objects.equals(e.getId(), eventId)) {
                newEvents.add(e);
            }
        }
        compilation.setEvents(newEvents);
        return CompilationDtoMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public CompilationDto addEventCompilation(Integer compilationId, Integer eventId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compilationId);
        if (compilationOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Compilation compilation = compilationOptional.get();

        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Event event = eventOptional.get();

        List<Event> events = new ArrayList<>(compilation.getEvents());
        events.add(event);
        compilation.setEvents(events);
        return CompilationDtoMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public void pinCompilation(Integer compilationId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compilationId);
        if (compilationOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Compilation compilation = compilationOptional.get();
        compilation.setShowOnMain(true);
        compilationRepository.save(compilation);
    }

    @Override
    public void unpinCompilation(Integer compilationId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compilationId);
        if (compilationOptional.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Compilation compilation = compilationOptional.get();
        compilation.setShowOnMain(false);
        compilationRepository.save(compilation);
    }
}
