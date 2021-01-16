package com.kiprenko.springfield.restcontroller;

import com.kiprenko.springfield.exception.UserNotFoundException;
import com.kiprenko.springfield.exception.UsernameAlreadyExists;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@ControllerAdvice
@Log4j2
public class ExceptionHandlingController {

    public static final String LOG_JSON_TEMPLATE = "{\n" +
            "  \"username\": \"%s\",\n" +
            "  \"IP\": \"%s\",\n" +
            "  \"timestamp\": \"%d\",\n" +
            "  \"exception\": \"%s\",\n" +
            "  \"message\": \"%s\",\n" +
            "  \"requestURI\": \"%s\"\n" +
            "}\n";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex,
                                                  HttpServletRequest request,
                                                  Principal principal) {
        LOGGER.error(generateLogMessage(ex, request, principal), ex);
        return getResponseEntity(ex.getClass().getName(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String generateLogMessage(Exception ex, HttpServletRequest request, Principal principal) {
        return String.format(LOG_JSON_TEMPLATE, principal.getName(),
                                                request.getRemoteAddr(),
                                                System.currentTimeMillis(),
                                                ex.getClass().getName(),
                                                ex.getMessage(),
                                                request.getRequestURI());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(Exception ex,
                                                     HttpServletRequest request,
                                                     Principal principal) {
        LOGGER.error(generateLogMessage(ex, request, principal), ex);
        String exMessage = ex.getMessage();
        return getResponseEntity(ex.getClass().getName(),
                exMessage == null ? "User not found in the database" : exMessage,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<Object> handleUsernameAlreadyExists(Exception ex,
                                                              HttpServletRequest request,
                                                              Principal principal) {
        LOGGER.error(generateLogMessage(ex, request, principal), ex);
        String exMessage = ex.getMessage();
        return getResponseEntity(ex.getClass().getName(),
                exMessage == null ? "Username already exists" : exMessage,
                HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> getResponseEntity(String exceptionClassName,
                                                     String exceptionMessage,
                                                     HttpStatus status) {
        return new ResponseEntity<>(String.format("%s: %s", exceptionClassName, exceptionMessage), status);
    }
}
