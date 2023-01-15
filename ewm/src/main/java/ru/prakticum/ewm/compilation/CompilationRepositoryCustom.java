package ru.prakticum.ewm.compilation;

import ru.prakticum.ewm.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepositoryCustom {
    List<Compilation> searchCompilations(Boolean pinned, Integer from, Integer size);
}
