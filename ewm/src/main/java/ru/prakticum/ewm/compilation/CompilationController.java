package ru.prakticum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.prakticum.ewm.compilation.dto.CompilationDto;

import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(path = "/compilations")
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getState(@RequestParam(required = false) Boolean pinned,
                                         @RequestParam(defaultValue = "1", required = false) Integer from,
                                         @RequestParam(defaultValue = "10", required = false) Integer size) {
        if (from < 0 || size < 0) {
            throw new ValidationException();
        }
        return compilationService.searchCompilations(pinned, from, size);
    }

    @GetMapping(value = "/{compilationId}")
    public CompilationDto get(@PathVariable int compilationId) {
        return compilationService.get(compilationId);
    }
}
