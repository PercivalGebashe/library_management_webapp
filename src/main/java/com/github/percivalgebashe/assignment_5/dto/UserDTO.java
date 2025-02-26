package com.github.percivalgebashe.assignment_5.dto;

import com.github.percivalgebashe.assignment_5.entity.User;
import com.github.percivalgebashe.assignment_5.enums.Roles;
import com.github.percivalgebashe.assignment_5.util.IdGenerator;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private boolean enabled;
    private Roles role;

    public User toEntity() {
        User userEntity = new User();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setEnabled(enabled);
        userEntity.setRole(role);

        return userEntity;
    }

    public void generateUserId() {

        id = IdGenerator.generateUserId(username);
    }
}

