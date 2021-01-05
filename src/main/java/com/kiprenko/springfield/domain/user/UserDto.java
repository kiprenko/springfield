package com.kiprenko.springfield.domain.user;

import lombok.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Value
@Projection(types = User.class, name = "User")
public class UserDto {
    Long id;
    String username;
    String firstName;
    String lastName;
    LocalDate birth;
    UserRole role;
}
