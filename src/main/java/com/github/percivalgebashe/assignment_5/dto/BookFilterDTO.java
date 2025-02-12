package com.github.percivalgebashe.assignment_5.dto;

import com.github.percivalgebashe.assignment_5.entity.Author;
import com.github.percivalgebashe.assignment_5.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookFilterDTO {
    private Set<Author> authors;

    private String title;

    private Set<Genre> genres;

    private Date publishedDateStart;

    private Date publishedDateEnd;
}
