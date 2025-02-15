package com.github.percivalgebashe.assignment_5.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum     Roles {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    Roles(String role){
        this.role = role;
    }
}
