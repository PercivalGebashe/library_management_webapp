package com.github.percivalgebashe.assignment_5.dto;

import com.github.percivalgebashe.assignment_5.entity.Role;
import com.github.percivalgebashe.assignment_5.entity.User;
import com.github.percivalgebashe.assignment_5.enums.Roles;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<Roles> roles;

    public User toEntity() {
        User userEntity = new User();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setEnabled(enabled);

        userEntity.setRoles(roles.stream()
                .map(roleEnum -> new Role(null, roleEnum))
                .collect(Collectors.toSet()));

        return userEntity;
    }
}

