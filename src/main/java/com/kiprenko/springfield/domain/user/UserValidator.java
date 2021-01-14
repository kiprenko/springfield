package com.kiprenko.springfield.domain.user;

public interface UserValidator {
    void validate(User user);

    void validatePassword(String password);
}
