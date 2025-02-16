package com.github.percivalgebashe.assignment_5.enums;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
public enum     Roles {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    Roles(String role){
        this.role = role;
    }

    public static Set<String> getRoles(){
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
