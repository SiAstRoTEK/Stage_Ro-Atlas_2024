package com.ProjectAPI.APIRestUsers.controller;

import com.ProjectAPI.APIRestUsers.entity.AuthResponse;
import com.ProjectAPI.APIRestUsers.entity.User;
import com.ProjectAPI.APIRestUsers.service.impl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User request) {
        authService.register(request);
        return ResponseEntity.ok("User successfully created");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
