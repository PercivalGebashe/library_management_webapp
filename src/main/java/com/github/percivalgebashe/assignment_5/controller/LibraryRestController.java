package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5.dto.BookDTO;
import com.github.percivalgebashe.assignment_5.dto.PaginatedResponse;
import com.github.percivalgebashe.assignment_5.exception.NoContentException;
import com.github.percivalgebashe.assignment_5.security.jwt.JwtUtil;
import com.github.percivalgebashe.assignment_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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
    public ResponseEntity<PaginatedResponse<BookDTO>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            Model model) {
        try {
            WebClient client = WebClient.builder().build();

            PaginatedResponse<BookDTO> booksPage = Objects.requireNonNull(client.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8082)
                            .path("/api/v1/books")
                            .queryParam("page", page)
                            .queryParam("size", size)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<PaginatedResponse<BookDTO>>() {})
                    .block()).getBody();

            return ResponseEntity.ok(booksPage);
        } catch (NoContentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        try {
            bookDTO.generateBookId();
            WebClient client = WebClient.builder().build();

            Objects.requireNonNull(client.post()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8082)
                            .path("/api/v1/books")
                            .build()).contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(bookDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(BookDTO.class)
                    .block()).getBody();
            return ResponseEntity.ok(bookDTO);
        }catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.badRequest().build();

        }

    }

    @PutMapping("/book/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        try {
            System.out.println("bookDTO: " + bookDTO);
            WebClient client = WebClient.builder().build();

            BookDTO book = Objects.requireNonNull(client.put()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8082)
                            .path("/api/v1/books/update")
                            .build())
                            .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(bookDTO)
                    .retrieve()
                    .toEntity(BookDTO.class)
                    .   block()).getBody();

            return ResponseEntity.ok(book);
        }catch (NoContentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/authors")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        try {
            System.out.println("Getting all authors");
            WebClient webClient = WebClient.builder().build();

             List<AuthorDTO> authors = Objects.requireNonNull(webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8082)
                            .path("/api/v1/authors")
                            .build())
                             .accept(MediaType.APPLICATION_JSON)
                             .retrieve()
                             .toEntity(new ParameterizedTypeReference<List<AuthorDTO>>() {})
                             .block().getBody()
                    );
             return ResponseEntity.ok(authors);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value = "/author", consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookDTO> addAuthor(@RequestBody BookDTO bookDTO) {
        try {
            WebClient client = WebClient.builder().build();

            AuthorDTO authorDTO = Objects.requireNonNull(client.post()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8082)
                            .path("/api/v1/author")
                            .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(bookDTO)
                    .retrieve()
                    .toEntity(AuthorDTO.class)
                    .block()).getBody();
            return ResponseEntity.ok(bookDTO);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("book/{searchType}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String searchType) {
        try {
            WebClient client = WebClient.builder().build();

            BookDTO bookDTO = Objects.requireNonNull(client.post()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8082)
                            .path("/api/v1/books")
                            .queryParam("searchType", searchType)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(BookDTO.class)
                    .block()).getBody();
            return ResponseEntity.ok(bookDTO);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/book")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookDTO> deleteBook(@RequestParam("id") String bookId) {
        try {
            WebClient client = WebClient.builder().build();

            Objects.requireNonNull(client.delete()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8082)
                            .path("/api/v1/books")
                            .queryParam("bookId", bookId)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(Object.class)
                    .block()).getBody();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}