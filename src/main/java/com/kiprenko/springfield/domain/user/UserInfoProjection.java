package com.kiprenko.springfield.domain.user;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(types = User.class, name = "UserInfoProjection")
public interface UserInfoProjection {
    Long getId();

    String getUsername();

    String getFirstName();

    String getLastName();

    LocalDate getBirth();

    UserRole getRole();
}
