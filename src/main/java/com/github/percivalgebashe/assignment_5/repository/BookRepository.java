package com.github.percivalgebashe.assignment_5.repository;

import com.github.percivalgebashe.assignment_5.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, CustomBookRepository, JpaSpecificationExecutor<Book> {
}
