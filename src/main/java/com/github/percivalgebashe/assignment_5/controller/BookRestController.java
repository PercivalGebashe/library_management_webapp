package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.dto.BookDTO;
import com.github.percivalgebashe.assignment_5.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5.entity.Book;
import com.github.percivalgebashe.assignment_5.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public Page<Book> getBooks() {
        return bookService.findAll(Pageable.ofSize(10));
    }

    @GetMapping(value = "books/filter", consumes = "application/json")
    public Page<Book> getBooksFilter(@RequestBody BookFilterDTO filter) {
        return bookService.findBookByFilter(filter, Pageable.ofSize(10));
    }

    @PostMapping(value = "/addbook", consumes = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book) {
        Book bookEntity = bookService.save(book);

        if (null != bookEntity) {
            return new ResponseEntity<>(bookEntity, HttpStatus.CREATED);
        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/edit", consumes = "application/json")
    public ResponseEntity<Book> editBook(@RequestBody BookDTO book) {
        Book bookEntity = bookService.save(book);
        if (null != bookEntity) {
            return new ResponseEntity<>(bookEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
