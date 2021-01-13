package com.kiprenko.springfield.domain.user;

public interface UserMapper {
    User convertDtoToUser(UserDto userDto);

    UserDto convertUserToDto(User user);
}
