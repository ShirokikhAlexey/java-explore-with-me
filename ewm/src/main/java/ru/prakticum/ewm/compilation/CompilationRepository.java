package ru.prakticum.ewm.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prakticum.ewm.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer>, CompilationRepositoryCustom {
}
