package com.kiprenko.springfield.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public void handleError(Exception ex) {
        LOGGER.error(ex.getClass().getName() + " occurred.", ex);
    }
}
