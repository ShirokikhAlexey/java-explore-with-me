package ru.prakticum.ewm.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.prakticum.ewm.category.dto.CategoryDto;
import ru.prakticum.ewm.compilation.dto.CompilationDto;
import ru.prakticum.ewm.compilation.dto.CompilationShortDto;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.event.dto.RequestDto;
import ru.prakticum.ewm.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/events")
    public List<EventDto> get(@RequestParam(required = false) List<Integer> users,
                              @RequestParam(required = false) List<String> states,
                              @RequestParam(required = false) List<Integer> categories,
                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  @RequestParam(required = false) LocalDateTime rangeStart,
                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                  @RequestParam(required = false) LocalDateTime rangeEnd,
                              @RequestParam(defaultValue = "1", required = false) Integer from,
                              @RequestParam(defaultValue = "10", required = false) Integer size) {
        return adminService.search(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping(value = "/events/{eventId}")
    public EventDto update(@PathVariable int eventId, @RequestBody EventDto eventDto) {
        return adminService.update(eventId, eventDto);
    }

    @PatchMapping(value = "/events/{eventId}/publish")
    public EventDto publish(@PathVariable int eventId) {
        return adminService.publish(eventId);
    }

    @PatchMapping(value = "/events/{eventId}/reject")
    public EventDto reject(@PathVariable int eventId) {
        return adminService.reject(eventId);
    }

    @PatchMapping(value = "/categories")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        return adminService.updateCategory(categoryDto);
    }

    @PostMapping(value = "/categories")
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return adminService.addCategory(categoryDto);
    }

    @DeleteMapping(value = "/categories/{categoryId}")
    public void deleteCategory(@PathVariable int categoryId) {
        adminService.deleteCategory(categoryId);
    }

    @GetMapping(value = "/users")
    public List<UserDto> getUsers(@RequestParam(required = false) List<Integer> ids,
                                  @RequestParam(defaultValue = "1", required = false) Integer from,
                                  @RequestParam(defaultValue = "10", required = false) Integer size) {
        return adminService.searchUsers(ids, from, size);
    }

    @PostMapping(value = "/users")
    public UserDto addUser(@RequestBody UserDto userDto) {
        return adminService.addUser(userDto);
    }

    @DeleteMapping(value = "/users/{userId}")
    public void deleteUser(@PathVariable int userId) {
        adminService.deleteUser(userId);
    }

    @PostMapping(value = "/compilations")
    public CompilationDto addCompilation(@RequestBody CompilationShortDto compilationShortDto) {
        return adminService.addCompilation(compilationShortDto);
    }

    @DeleteMapping(value = "/compilations/{compilationId}")
    public void deleteCompilation(@PathVariable int compilationId) {
        adminService.deleteCompilation(compilationId);
    }

    @DeleteMapping(value = "/compilations/{compilationId}/events/{eventId}")
    public CompilationDto deleteCompilationEvent(@PathVariable int compilationId, @PathVariable int eventId) {
        return adminService.deleteCompilationEvent(compilationId, eventId);
    }

    @PatchMapping(value = "/compilations/{compilationId}/events/{eventId}")
    public CompilationDto addCompilationEvent(@PathVariable int compilationId, @PathVariable int eventId) {
        return adminService.addEventCompilation(compilationId, eventId);
    }

    @PatchMapping(value = "/compilations/{compilationId}/pin")
    public void pinCompilation(@PathVariable int compilationId) {
        adminService.pinCompilation(compilationId);
    }

    @DeleteMapping(value = "/compilations/{compilationId}/pin")
    public void unpinCompilation(@PathVariable int compilationId) {
        adminService.unpinCompilation(compilationId);
    }

}
