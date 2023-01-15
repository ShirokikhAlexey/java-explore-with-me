package ru.prakticum.ewm.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.prakticum.ewm.category.dto.CategoryDto;
import ru.prakticum.ewm.compilation.dto.CompilationDto;

import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> get(@RequestParam(defaultValue = "1", required = false) Integer from,
                                 @RequestParam(defaultValue = "10", required = false) Integer size) {
        if (from < 0 || size < 0) {
            throw new ValidationException();
        }
        return categoryService.get(from, size);
    }

    @GetMapping(value = "/{categoryId}")
    public CategoryDto get(@PathVariable int categoryId) {
        return categoryService.get(categoryId);
    }
}
