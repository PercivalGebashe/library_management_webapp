package com.github.percivalgebashe.assignment_5.dto;

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
    private Set<AuthorDTO> authors;

    private String title;

    private String genres;

    private Date publishedDateStart;

    private Date publishedDateEnd;
}
