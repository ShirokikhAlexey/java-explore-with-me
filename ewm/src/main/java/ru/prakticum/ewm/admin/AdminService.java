package ru.prakticum.ewm.admin;

import ru.prakticum.ewm.category.dto.CategoryDto;
import ru.prakticum.ewm.compilation.dto.CompilationDto;
import ru.prakticum.ewm.compilation.dto.CompilationShortDto;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.RequestDto;
import ru.prakticum.ewm.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.LongSummaryStatistics;

public interface AdminService {
    List<EventDto> search(List<Integer> users, List<String> states, List<Integer> categories,
                          LocalDateTime rangeStart,
                          LocalDateTime rangeEnd, Integer from,
                          Integer size);

    EventDto update(Integer eventId, EventDto eventDto);

    EventDto publish(Integer eventId);

    EventDto reject(Integer eventId);

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(Integer categoryId);

    List<UserDto> searchUsers(List<Integer> users, Integer from, Integer size);

    UserDto addUser(UserDto userDto);

    void deleteUser(Integer userId);

    CompilationDto addCompilation(CompilationShortDto compilationShortDto);

    void deleteCompilation(Integer compilationId);

    void deleteCompilationEvent(Integer compilationId, Integer eventId);

    void addEventCompilation(Integer compilationId, Integer eventId);

    void pinCompilation(Integer compilationId);

    void unpinCompilation(Integer compilationId);
}
