package ru.prakticum.ewm.category.dto;

import ru.prakticum.ewm.category.model.Category;

import javax.validation.ValidationException;

public final class CategoryDtoMapper {
    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category fromDto(CategoryDto categoryDto) {
        try {
            return new Category(categoryDto.getId(), categoryDto.getName());
        } catch (NullPointerException e) {
            throw new ValidationException();
        }
    }

    public static Category updateFromDto(Category category, CategoryDto categoryDto) {
        try {
            category.setName(categoryDto.getName());
            return category;
        } catch (NullPointerException e) {
            throw new ValidationException();
        }
    }
}
