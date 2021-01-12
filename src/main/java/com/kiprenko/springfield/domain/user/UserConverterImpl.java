package com.kiprenko.springfield.domain.user;

import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    @Override
    public User convertDtoToUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return null;
    }
}
