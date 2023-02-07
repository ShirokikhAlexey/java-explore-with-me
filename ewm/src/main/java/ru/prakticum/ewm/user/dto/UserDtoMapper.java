package ru.prakticum.ewm.user.dto;

import ru.prakticum.ewm.user.model.User;

public final class UserDtoMapper {
    public static User fromDto(UserShortDto userShortDto) {
        return new User(userShortDto.getId(), userShortDto.getName());
    }

    public static UserShortDto toDtoShort(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User fromDto(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }
}
