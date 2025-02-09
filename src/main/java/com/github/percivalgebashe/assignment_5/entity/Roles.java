package com.github.percivalgebashe.assignment_5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Roles {
    ROLE_ADMIN0(1L),
    ROLE_USER(2L);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;
}
