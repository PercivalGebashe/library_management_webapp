package com.github.percivalgebashe.assignment_5.repository;

import com.github.percivalgebashe.assignment_5.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
