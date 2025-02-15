package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.dto.UserDTO;
import com.github.percivalgebashe.assignment_5.entity.User;
import com.github.percivalgebashe.assignment_5.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@EnableMethodSecurity
@RestController
@RequestMapping("/api/v1/library")
public class LibraryRestController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public LibraryRestController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping(value = "/admin/add", consumes = "application/json")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        System.out.println("Adding user: " + userDTO);
        Optional<User> userOptional = userServiceImpl.registerUser(userDTO.toEntity());

        return userOptional
                .map(value -> ResponseEntity.ok("User added: " + value))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/user/books/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> getAllBooks() {
        return ResponseEntity.ok("All books available");
    }

    @PostMapping(value = "/admin/login", consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
//        try {
//
//        }
        return ResponseEntity.ok("Login successful");
    }

}
