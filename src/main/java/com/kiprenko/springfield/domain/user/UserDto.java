package com.kiprenko.springfield.domain.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birth;
    private UserRole role;
}
