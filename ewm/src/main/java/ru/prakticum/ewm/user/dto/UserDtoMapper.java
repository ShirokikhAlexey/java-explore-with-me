package ru.prakticum.ewm.user.dto;

import ru.prakticum.ewm.user.model.User;

import javax.validation.ValidationException;

public final class UserDtoMapper {
    public static User fromDto(UserShortDto userShortDto) {
        try {
            return new User(userShortDto.getId(), userShortDto.getName());
        } catch (NullPointerException e) {
            throw new ValidationException();
        }

    }

    public static UserShortDto toDtoShort(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User fromDto(UserDto userDto) {
        try {
            return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
        } catch (NullPointerException e) {
            throw new ValidationException();
        }

    }
}
