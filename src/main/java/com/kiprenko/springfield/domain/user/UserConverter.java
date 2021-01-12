package com.kiprenko.springfield.domain.user;

public interface UserConverter {
    User convertDtoToUser(UserDto userDto);

    UserDto convertUserToDto(User user);
}
