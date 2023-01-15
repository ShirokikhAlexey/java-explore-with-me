package ru.prakticum.ewm.category.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;

    public CategoryDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
