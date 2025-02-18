package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.dto.BookDTO;
import com.github.percivalgebashe.assignment_5.exception.NoContentException;
import com.github.percivalgebashe.assignment_5.security.jwt.JwtUtil;
import com.github.percivalgebashe.assignment_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@EnableMethodSecurity
@RestController
@RequestMapping("/api/v1/library")
public class LibraryRestController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LibraryRestController(UserService userService,
                                 AuthenticationManager authenticationManager,
                                 UserDetailsService userDetailsService,
                                 JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Objects> getAllBooks(Model model) {
        try {
            WebClient client = WebClient.create();

            List<BookDTO> booksList = Objects.requireNonNull(client.get()
                    .uri("http://localhost:8082/api/v1/books")
                    .accept(MediaType.parseMediaType("application/json"))
                    .retrieve()
                    .toEntityList(BookDTO.class)
                    .block()).getBody();

            System.out.println(booksList);
            model.addAttribute("books", booksList);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (NoContentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
