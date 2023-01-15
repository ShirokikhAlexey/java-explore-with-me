package ru.prakticum.ewm.category;

import ru.prakticum.ewm.category.dto.CategoryDto;
import ru.prakticum.ewm.compilation.dto.CompilationDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> get(Integer from, Integer size);

    CategoryDto get(Integer categoryId);
}
