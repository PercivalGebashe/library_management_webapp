package com.github.percivalgebashe.assignment_5.entity;

import com.github.percivalgebashe.assignment_5.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class User implements Serializable {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    private boolean enabled;
}
