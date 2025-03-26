package io.onicodes.issue_tracker.controllers.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleIssueNotFound(IssueNotFoundException e) {
        Map<String, String> errorRes= new HashMap<>();
        errorRes.put("error", "Not found");
        errorRes.put("message", e.getMessage());
        return new ResponseEntity<>(errorRes, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAppUserNotFound(AppUserNotFoundException e) {
        Map<String, String> errorRes= new HashMap<>();
        errorRes.put("error", "Not found");
        errorRes.put("message", e.getMessage());
        return new ResponseEntity<>(errorRes, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(MethodArgumentNotValidException e) {
        Map<String, String> errorRes = new HashMap<>();
        e.getFieldErrors()
         .forEach(error -> 
            errorRes.put(error.getField(), error.getDefaultMessage()));
        
        return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
    }
}
