package com.github.percivalgebashe.assignment_5.service;

import com.github.percivalgebashe.assignment_5.entity.BookCover;
import com.github.percivalgebashe.assignment_5.repository.BookCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookCoverService {

    private final BookCoverRepository bookCoverRepository;

    @Autowired
    public BookCoverService(BookCoverRepository bookCoverRepository) {
        this.bookCoverRepository = bookCoverRepository;
    }

    public Optional<BookCover> getBookCover(Long id) {
        return bookCoverRepository.findById(id);
    }
}
