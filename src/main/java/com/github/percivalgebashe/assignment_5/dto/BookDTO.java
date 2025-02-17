package com.github.percivalgebashe.assignment_5.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class BookDTO {
    private String id;
    private List<AuthorDTO> authors;
    private String title;
    private LocalDate publishedDate;
    private String description;
    private String isbn;
    private String genres;
    private Set<PublisherDTO> publishers;
}