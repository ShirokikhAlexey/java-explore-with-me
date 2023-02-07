package ru.prakticum.ewm.category.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private Integer id;
    private String name;

    public CategoryDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto(String name) {
        this.name = name;
    }

    public CategoryDto(Integer id) {
        this.id = id;
    }
}
