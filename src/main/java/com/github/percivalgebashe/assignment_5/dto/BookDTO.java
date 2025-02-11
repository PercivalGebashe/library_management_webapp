package com.github.percivalgebashe.assignment_5.dto;

import com.github.percivalgebashe.assignment_5.entity.Author;
import com.github.percivalgebashe.assignment_5.entity.Genre;
import com.github.percivalgebashe.assignment_5.entity.Publisher;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class BookDTO {
    private Long book_id;

    private Set<Author> authors;

    private String title;

    private Date publishedDate;

    private Set<Publisher> publishers;

    private String description;

    private String isbn;

    private Set<Genre> genres;
}
