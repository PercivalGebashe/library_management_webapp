package com.github.percivalgebashe.assignment_5.repository;

import com.github.percivalgebashe.assignment_5.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
