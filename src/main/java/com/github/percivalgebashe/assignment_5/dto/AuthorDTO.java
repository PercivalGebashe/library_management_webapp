package com.github.percivalgebashe.assignment_5.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthorDTO {
    private String id;
    private String name;
    private List<BookDTO> books;
}
