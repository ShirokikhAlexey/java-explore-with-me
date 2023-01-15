package ru.prakticum.ewm.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prakticum.ewm.category.model.Category;
import ru.prakticum.ewm.compilation.model.Compilation;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {
}
