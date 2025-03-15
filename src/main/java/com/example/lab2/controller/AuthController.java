package com.example.lab2.controller;

import com.example.lab2.dto.UserLoginDTO;
import com.example.lab2.dto.UserRegisterDTO;
import com.example.lab2.exception.UserAlreadyExistsException;
import com.example.lab2.model.UserEntity;
import com.example.lab2.service.JwtService;
import com.example.lab2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            userService.register(userRegisterDTO);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDTO.getEmail(), userLoginDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken((UserEntity) authentication.getPrincipal()));
        } else {
            return ResponseEntity.status(404).body("Incorrect account credentials");
        }
    }
}
