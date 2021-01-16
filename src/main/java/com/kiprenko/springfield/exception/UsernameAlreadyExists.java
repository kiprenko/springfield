package com.kiprenko.springfield.exception;

public class UsernameAlreadyExists extends Exception {
    public UsernameAlreadyExists() {
        super();
    }

    public UsernameAlreadyExists(String message) {
        super(message);
    }

    public UsernameAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameAlreadyExists(Throwable cause) {
        super(cause);
    }

    protected UsernameAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
