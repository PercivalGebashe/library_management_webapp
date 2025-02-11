package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.entity.BookCover;
import com.github.percivalgebashe.assignment_5.service.BookCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images/book_cover")
public class BookCoverController {

    @Autowired
    private BookCoverService bookCoverService;

    @GetMapping("/{id}")
    public ResponseEntity<BookCover> getBookCover(@PathVariable("id") Long id) {
        Optional<BookCover> bookCover = bookCoverService.getBookCover(id);
        return bookCover.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
