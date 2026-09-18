package com.example.ecommerce.repository;

import com.example.ecommerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsernameIgnoreCase(String username);
}