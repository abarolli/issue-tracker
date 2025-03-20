package io.onicodes.issue_tracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.onicodes.issue_tracker.dtos.RegistrationRequestDto;
import io.onicodes.issue_tracker.services.AppUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("/auth")
public class RegistrationController {
    
    private final AppUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDto requestDto) {
        userService.create(requestDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
