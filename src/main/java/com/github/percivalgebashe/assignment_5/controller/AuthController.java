package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.dto.UserDTO;
import com.github.percivalgebashe.assignment_5.record.LoginForm;
import com.github.percivalgebashe.assignment_5.security.jwt.JwtAuthenticationFilter;
import com.github.percivalgebashe.assignment_5.security.jwt.JwtUtil;
import com.github.percivalgebashe.assignment_5.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:8081")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        try {
            System.out.println("Authenticating");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password()));

            if (authentication.isAuthenticated()) {
                System.out.println("login successful".trim());
                // Generate JWT token
                String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(loginForm.username()));
                System.out.println("Token: " + token);

                // Add the JWT token to the cookie
                jwtAuthenticationFilter.addJwtToCookie(response, token);
                System.out.println("Added added token to cookies");

                // Return a success response, but no token in the body
//                System.out.println("Returning response and redirecting");
//                ResponseEntity.status(HttpStatus.FOUND);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("User logged in");
            } else {
                System.out.println("login failed".trim());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/admin/add", consumes = "application/json")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.ok("User added successfully");
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}