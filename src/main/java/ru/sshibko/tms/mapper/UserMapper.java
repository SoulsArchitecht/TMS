package ru.sshibko.tms.mapper;

import ru.sshibko.tms.dto.UserDto;
import ru.sshibko.tms.model.User;

import java.io.Serializable;

public class UserMapper implements Serializable {

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());

        return userDto;
    }

    public static User mapToUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());

        return user;
    }
}
