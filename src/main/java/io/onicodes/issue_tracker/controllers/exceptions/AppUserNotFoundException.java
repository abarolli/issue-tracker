package io.onicodes.issue_tracker.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(Long id) {
        super("User with ID " + id + " not found");
    }
}
