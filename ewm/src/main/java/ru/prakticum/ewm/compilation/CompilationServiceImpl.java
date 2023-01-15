package ru.prakticum.ewm.compilation;

import org.springframework.stereotype.Service;
import ru.prakticum.ewm.compilation.dto.CompilationDto;
import ru.prakticum.ewm.compilation.dto.CompilationDtoMapper;
import ru.prakticum.ewm.compilation.model.Compilation;
import ru.prakticum.ewm.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompilationServiceImpl implements CompilationService{
    private final CompilationRepository repository;

    public CompilationServiceImpl(CompilationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CompilationDto> searchCompilations(Boolean pinned, Integer from, Integer size) {
        List<CompilationDto> result = new ArrayList<>();
        List<Compilation> compilations = repository.searchCompilations(pinned, from, size);
        for (Compilation c : compilations) {
            result.add(CompilationDtoMapper.toDto(c));
        }
        return result;
    }

    @Override
    public CompilationDto get(Integer compilationId) {
        Optional<Compilation> compilation = repository.findById(compilationId);
        if(compilation.isEmpty()) {
            throw new NotFoundException();
        }
        return CompilationDtoMapper.toDto(compilation.get());
    }
}
