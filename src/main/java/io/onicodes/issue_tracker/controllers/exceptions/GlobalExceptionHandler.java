package io.onicodes.issue_tracker.controllers.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleIssueNotFound(IssueNotFoundException e) {
        Map<String, String> errorRes= new HashMap<>();
        errorRes.put("error", "Not found");
        errorRes.put("message", e.getMessage());
        return new ResponseEntity<>(errorRes, HttpStatus.NOT_FOUND);
    }
}
