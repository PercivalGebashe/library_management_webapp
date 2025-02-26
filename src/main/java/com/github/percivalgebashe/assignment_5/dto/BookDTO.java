package com.github.percivalgebashe.assignment_5.dto;

import com.github.percivalgebashe.assignment_5.util.IdGenerator;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private String publishers;

    public void generateBookId() {
        if (authors != null && !authors.isEmpty()) {

             id = IdGenerator.generateBookId(authors.stream().map(AuthorDTO::getName).toList(),title,publishedDate);
        }
    }
}