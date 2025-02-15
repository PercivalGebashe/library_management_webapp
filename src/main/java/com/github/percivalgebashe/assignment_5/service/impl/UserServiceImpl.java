package com.github.percivalgebashe.assignment_5.service.impl;

import com.github.percivalgebashe.assignment_5.entity.Role;
import com.github.percivalgebashe.assignment_5.entity.User;
import com.github.percivalgebashe.assignment_5.enums.Roles;
import com.github.percivalgebashe.assignment_5.repository.RoleRepository;
import com.github.percivalgebashe.assignment_5.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<User> registerUser(User user) {
        if(user.getPassword() == null || !user.getPassword().isEmpty() &&
            user.getUsername() == null || !user.getUsername().isEmpty() &&
            user.getRole() != null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                System.out.println("Saving user: " + user);

            Role existingRole = roleRepository.findByName(user.getRole())
                            .orElseThrow(() -> new RuntimeException(String.format("Role %s not found", user.getRole().getName().getRole())));

            user.setRole(existingRole);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }
}
