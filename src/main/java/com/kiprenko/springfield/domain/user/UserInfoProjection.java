package com.kiprenko.springfield.domain.user;

import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

@ApiModel(value = "User Information",
        description = "Information about a user")
public interface UserInfoProjection {
    Long getId();

    String getUsername();

    String getFirstName();

    String getLastName();

    LocalDate getBirth();

    UserRole getRole();
}
