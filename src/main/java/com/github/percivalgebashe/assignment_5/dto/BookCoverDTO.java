package com.github.percivalgebashe.assignment_5.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookCoverDTO {
    private String bookId;
    private String imagePath;
}
