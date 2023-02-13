package ru.prakticum.ewm.category;

import ru.prakticum.ewm.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> get(Integer from, Integer size);

    CategoryDto get(Integer categoryId);
}
