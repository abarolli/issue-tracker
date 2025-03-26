package io.onicodes.issue_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.services.AppUserService;

@RestController
@RequestMapping("/users")
public class AppUserController {
    
    @Autowired
    private AppUserService userService;

    @GetMapping
    public Page<AppUserDto> getUsers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        
        return userService.getUsers(page, size);
    }

    @GetMapping("/{id}")
    public AppUserDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

}
