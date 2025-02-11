package com.github.percivalgebashe.assignment_5.service;

import com.github.percivalgebashe.assignment_5.entity.Book;
import com.github.percivalgebashe.assignment_5.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
