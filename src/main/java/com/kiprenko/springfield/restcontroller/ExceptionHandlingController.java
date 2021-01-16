package com.kiprenko.springfield.restcontroller;

import com.kiprenko.springfield.exception.UserNotFoundException;
import com.kiprenko.springfield.exception.UsernameAlreadyExists;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@ControllerAdvice
@Log4j2
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex,
                                                  HttpServletRequest request,
                                                  Principal principal) {
        LOGGER.error(generateLogMessage(ex, request, principal), ex);
        return getResponseEntity(ex.getClass().getName(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String generateLogMessage(Exception ex, HttpServletRequest request, Principal principal) {
        JSONObject logJson = new JSONObject();
        logJson.put("username", principal.getName());
        logJson.put("IP", request.getRemoteAddr());
        logJson.put("timestamp", System.currentTimeMillis());
        logJson.put("exception", ex.getClass().getName());
        logJson.put("message", ex.getMessage());
        logJson.put("requestURI", request.getRequestURI());
        return logJson.toJSONString();
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
