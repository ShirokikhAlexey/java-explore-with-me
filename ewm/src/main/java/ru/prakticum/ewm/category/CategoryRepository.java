package ru.prakticum.ewm.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prakticum.ewm.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {
}
