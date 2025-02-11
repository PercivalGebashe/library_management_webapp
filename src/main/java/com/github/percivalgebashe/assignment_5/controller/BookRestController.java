package com.github.percivalgebashe.assignment_5.controller;

import com.github.percivalgebashe.assignment_5.entity.Book;
import com.github.percivalgebashe.assignment_5.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public Page<Book> getBooks() {
        return bookService.getBooks(Pageable.ofSize(10));
    }
}
