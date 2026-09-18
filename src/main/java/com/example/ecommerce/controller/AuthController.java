package com.example.ecommerce.controller;

import com.example.ecommerce.dto.LoginRequestDTO;
import com.example.ecommerce.dto.UserResponseDTO;
import com.example.ecommerce.model.Users;
import com.example.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.security.JwtService;
import com.example.ecommerce.dto.LoginResponseDTO;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final JwtService jwtService;

    private final UserService userService;

    public AuthController(UserService userService,
                      JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
}
    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody Users user) {

        Users savedUser = userService.registerUser(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }

    @PostMapping("/login")
public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {

    Users user = userService.loginUser(
            loginRequest.getUsername(),
            loginRequest.getPassword()
    );

    String token = jwtService.generateToken(
            user.getUsername(),
            user.getRole()
    );

    return new LoginResponseDTO(
            token,
            user.getId(),
            user.getUsername(),
            user.getRole()
    );
}
}