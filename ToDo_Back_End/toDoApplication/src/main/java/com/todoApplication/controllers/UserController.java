package com.todoApplication.controllers;

import com.todoApplication.datas.models.User;
import com.todoApplication.dtos.requests.RegistrationRequest;
import com.todoApplication.dtos.requests.TaskRequestDTO;
import com.todoApplication.dtos.responses.TaskResponseDTO;
import com.todoApplication.dtos.responses.UserResponseDTO;
import com.todoApplication.servises.TaskService;
import com.todoApplication.servises.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody RegistrationRequest registrationRequest) {
        if(registrationRequest.getEmail() == null || registrationRequest.getEmail().isEmpty() || registrationRequest.getPassword() == null || registrationRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        User registeredUser = userService.registerUser(registrationRequest);
        return ResponseEntity.ok(registeredUser);

    }
}
