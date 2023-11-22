package edu.uclm.esi.ds.webApp.security.config;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        // Handle ResponseStatusException, which includes setting custom responses for specific HTTP status codes
        HttpStatusCode status = ex.getStatusCode();
        String errorMessage = "Error occurred: " + ex.getReason();
        return ResponseEntity.status(status).body(errorMessage);
    }
}