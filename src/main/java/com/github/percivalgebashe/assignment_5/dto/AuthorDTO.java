package com.github.percivalgebashe.assignment_5.dto;

import com.github.percivalgebashe.assignment_5.util.IdGenerator;
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
    private String biography;

    public void generateAuthorId(){
        if(id == null) {
            generateAuthorId();
        }
    }
}