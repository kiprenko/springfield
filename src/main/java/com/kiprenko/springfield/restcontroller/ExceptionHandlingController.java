package com.kiprenko.springfield.restcontroller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        LOGGER.error(ex);
        return new ResponseEntity<>(String.format("%s: %s", ex.getClass().getName(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
