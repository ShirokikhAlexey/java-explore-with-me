package ru.prakticum.ewm.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.prakticum.ewm.category.dto.CategoryDto;
import ru.prakticum.ewm.compilation.dto.CompilationDto;
import ru.prakticum.ewm.compilation.dto.CompilationShortDto;
import ru.prakticum.ewm.event.dto.EventDto;
import ru.prakticum.ewm.location.LocationService;
import ru.prakticum.ewm.location.model.Location;
import ru.prakticum.ewm.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private final AdminService adminService;
    private final LocationService locationService;

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

    @PatchMapping(value = "/events/{eventId}")
    public EventDto update(@PathVariable int eventId, @RequestBody EventDto eventDto) {
        return adminService.update(eventId, eventDto);
    }

    @PatchMapping(value = "/categories/{categoryId}")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable int categoryId) {
        categoryDto.setId(categoryId);
        return adminService.updateCategory(categoryDto);
    }

    @PostMapping(value = "/categories")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return adminService.addCategory(categoryDto);
    }

    @DeleteMapping(value = "/categories/{categoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
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
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) {
        return adminService.addUser(userDto);
    }

    @DeleteMapping(value = "/users/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int userId) {
        adminService.deleteUser(userId);
    }

    @PostMapping(value = "/compilations")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody CompilationShortDto compilationShortDto) {
        return adminService.addCompilation(compilationShortDto);
    }

    @DeleteMapping(value = "/compilations/{compilationId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable int compilationId) {
        adminService.deleteCompilation(compilationId);
    }

    @PatchMapping(value = "/compilations/{compilationId}")
    public void updateCompilation(@PathVariable int compilationId, @RequestBody CompilationShortDto compilationShortDto) {
        adminService.updateCompilation(compilationId, compilationShortDto);
    }

    @GetMapping(value = "/locations")
    public List<Location> getLocations(@RequestParam Float lat,
                                       @RequestParam Float lon,
                                       @RequestParam(defaultValue = "1", required = false) Integer from,
                                       @RequestParam(defaultValue = "10", required = false) Integer size) {
        return locationService.get(lat, lon, from, size);
    }

    @PostMapping(value = "/locations")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Location addLocation(@RequestBody Location location) {
        return locationService.create(location);
    }

    @PatchMapping(value = "/locations/{locationId}")
    public Location updateLocation(@RequestBody Location location, @PathVariable int locationId) {
        return locationService.update(locationId, location);
    }

    @GetMapping(value = "/locations/search")
    public List<Location> searchLocations(@RequestParam Float lat,
                                          @RequestParam Float lon,
                                          @RequestParam(required = false, defaultValue = "1") Float radius,
                                          @RequestParam(defaultValue = "1", required = false) Integer from,
                                          @RequestParam(defaultValue = "10", required = false) Integer size) {
        return locationService.search(lat, lon, radius, from, size);
    }
}
