package com.github.percivalgebashe.assignment_5.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AuthorDTO {
    private String id;
    private String name;
    private LocalDate birthDate;
    private List<BookDTO> books;

    public void generateAuthorId() {
        id = name.replaceAll("\\s+", "") + "_" + birthDate.getYear();
    }
}
