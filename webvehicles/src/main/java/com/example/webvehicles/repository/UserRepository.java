package com.example.webvehicles.repository;

import com.example.webvehicles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);

    List<User> findAllByRole(String role);
}
