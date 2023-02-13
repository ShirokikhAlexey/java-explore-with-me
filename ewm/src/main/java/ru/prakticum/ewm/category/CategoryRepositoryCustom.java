package ru.prakticum.ewm.category;

import ru.prakticum.ewm.category.model.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> searchCategories(Integer from, Integer size);
}
