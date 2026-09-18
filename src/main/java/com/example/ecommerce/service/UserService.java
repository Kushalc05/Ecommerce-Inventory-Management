package com.example.ecommerce.service;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

public UserService(UserRepository userRepository,
                   PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
}

    public Users registerUser(Users user) {

        // Username validation
        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        // Password validation
        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        String username = user.getUsername().trim();

        // Check if username already exists
        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Set cleaned username
        user.setUsername(username);

        // Hash password before saving
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        // Every newly registered user starts as CUSTOMER
        user.setRole("CUSTOMER");

        return userRepository.save(user);
    }

    public Users loginUser(String username, String password) {

    if (username == null || username.trim().isEmpty()) {
        throw new IllegalArgumentException("Username is required");
    }

    if (password == null || password.trim().isEmpty()) {
        throw new IllegalArgumentException("Password is required");
    }

    Users user = userRepository
            .findByUsernameIgnoreCase(username.trim())
            .orElseThrow(() ->
                    new IllegalArgumentException("Invalid username or password"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
        throw new IllegalArgumentException("Invalid username or password");
    }

    return user;
}

}