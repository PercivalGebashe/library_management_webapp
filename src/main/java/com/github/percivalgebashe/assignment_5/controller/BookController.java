package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.dto.BookDTO;
import com.github.percivalgebashe.assignment_5.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5.entity.Book;
import com.github.percivalgebashe.assignment_5.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<Book> getBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PostMapping("/filter")
    public Page<Book> getBooksFilter(@RequestBody BookFilterDTO filter, Pageable pageable) {
        return bookService.findBookByFilter(filter, pageable);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book) {
        Book bookEntity = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookEntity);
    }

    @PutMapping(value = "/edit", consumes = "application/json")
    public ResponseEntity<Book> editBook(@RequestBody BookDTO bookDTO) {
        try {
            Book updatedBook = bookService.updateBook(bookDTO);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(String.format("User with ID %s not found", bookDTO.getBook_id()));
        }
    }
}