package com.kiprenko.springfield.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@Component
public class UserValidatorImpl implements UserValidator {

    public static final String USERNAME_AND_PASSWORD_REGEX = "[a-zA-Z0-9]+";
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 30;
    private final Validator validator;

    @Autowired
    public UserValidatorImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void validate(User user) {
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        Optional<ConstraintViolation<User>> constraintViolationOpt = constraintViolations.stream().findFirst();
        if (constraintViolationOpt.isPresent()) {
            ConstraintViolation<User> constraintViolation = constraintViolationOpt.get();
            String msg = String.format("%s field %s", constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password field can't be null or blank");
        }

        int passwordLength = password.length();
        if (passwordLength < MIN_PASSWORD_LENGTH || passwordLength > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(String.format("password field must be greater than %d and less than %d",
                    MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
        }

        if (!password.matches(USERNAME_AND_PASSWORD_REGEX)) {
            throw new IllegalArgumentException(String.format("password field = must match %s", USERNAME_AND_PASSWORD_REGEX));
        }
    }
}
