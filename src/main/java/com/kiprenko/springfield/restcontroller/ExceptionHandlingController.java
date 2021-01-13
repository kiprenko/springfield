package com.kiprenko.springfield.restcontroller;

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
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request, Principal principal) {
        LOGGER.error(generateLogMessage(ex, request, principal), ex);
        return new ResponseEntity<>(String.format("%s: %s", ex.getClass().getName(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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
}
