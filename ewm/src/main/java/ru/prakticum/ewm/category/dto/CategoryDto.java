package ru.prakticum.ewm.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;

    public CategoryDto(String name) {
        this.name = name;
    }

    public CategoryDto(Integer id) {
        this.id = id;
    }
}
