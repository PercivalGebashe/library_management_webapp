package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.dto.BookDTO;
import com.github.percivalgebashe.assignment_5.exception.NoContentException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class WebController {
    @GetMapping("/login")
    public String login() {
        System.out.println(1);
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        try {
            System.out.println(4);
            WebClient client = WebClient.create();

            List<Map<String, Object>> booksList = client.get()
                    .uri("http://localhost:8082/api/v1/books")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                    })
                    .block();

            booksList.stream()
                    .map(book -> {
                        BookDTO bookDTO = new BookDTO();
                        bookDTO.setBook_id((Long) book.get("id"));
                        bookDTO.setTitle((String) book.get("title"));
                        bookDTO.getAuthors().add((String) book.get("author"));
                        bookDTO.setPublishedDate((LocalDate) book.get("publishedDate"));
                        bookDTO.setIsbn((String) book.get("isbn"));
                        bookDTO.setDescription((String) book.get("description"));
                        bookDTO.getPublishers().add((String) book.get("publisher"));
                        bookDTO.setGenres((Set<Object>) book.get("genres"));
                        return bookDTO;
                    }).toList();
            System.out.println(booksList);

            return "home";
        }catch (NoContentException e){
            throw e;
        }
    }
}