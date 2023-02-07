package ru.prakticum.ewm.user.dto;

import lombok.Data;

@Data
public class UserShortDto {
    private Integer id;
    private String name;

    public UserShortDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
