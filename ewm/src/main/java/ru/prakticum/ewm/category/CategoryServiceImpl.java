package ru.prakticum.ewm.category;

import org.springframework.stereotype.Service;
import ru.prakticum.ewm.category.dto.CategoryDto;
import ru.prakticum.ewm.category.dto.CategoryDtoMapper;
import ru.prakticum.ewm.category.model.Category;
import ru.prakticum.ewm.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoryDto> get(Integer from, Integer size) {
        List<CategoryDto> result = new ArrayList<>();
        List<Category> categories = repository.searchCategories(from, size);
        for (Category c : categories) {
            result.add(CategoryDtoMapper.toDto(c));
        }
        return result;
    }

    @Override
    public CategoryDto get(Integer categoryId) {
        Optional<Category> category = repository.findById(categoryId);
        if(category.isEmpty()) {
            throw new NotFoundException();
        }
        return CategoryDtoMapper.toDto(category.get());
    }
}
