package ru.prakticum.ewm.category.dto;

import ru.prakticum.ewm.category.model.Category;

public class CategoryDtoMapper {
    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category fromDto(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public static Category updateFromDto(Category category, CategoryDto categoryDto) {
        category.setName(categoryDto.getName());
        return category;
    }
}
