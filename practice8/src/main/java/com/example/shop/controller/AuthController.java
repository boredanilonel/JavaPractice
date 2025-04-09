package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @PostMapping("/register-admin")
    public ResponseEntity<User> registerAdmin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        return ResponseEntity.ok(authService.registerAdmin(username, password));
    }
}
