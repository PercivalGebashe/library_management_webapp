package com.github.percivalgebashe.assignment_5.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "roles_id")
    private Long rolesId;
}
