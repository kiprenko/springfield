package com.kiprenko.springfield.domain.user;

import java.time.LocalDate;

public interface UserInfoProjection {
    Long getId();

    String getUsername();

    String getFirstName();

    String getLastName();

    LocalDate getBirth();

    UserRole getRole();
}
