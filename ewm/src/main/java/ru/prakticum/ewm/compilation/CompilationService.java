package ru.prakticum.ewm.compilation;

import ru.prakticum.ewm.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> searchCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto get(Integer compilationId);
}
