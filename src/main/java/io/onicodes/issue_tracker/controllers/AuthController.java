package io.onicodes.issue_tracker.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.onicodes.issue_tracker.dtos.AuthRequestDto;
import io.onicodes.issue_tracker.dtos.AuthResponseDto;
import io.onicodes.issue_tracker.security.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto credentials) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = jwtService.generateToken(credentials.getUsername());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
