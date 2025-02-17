package com.github.percivalgebashe.assignment_5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BookDTO {
    private Long book_id;

    private List<Object> authors;

    private String title;

    private LocalDate publishedDate;

    private Set<Object> publishers;

    private String description;

    private String isbn;

    private Set<Object> genres;
}
