package com.github.percivalgebashe.assignment_5.service;

import com.github.percivalgebashe.assignment_5.entity.Role;
import com.github.percivalgebashe.assignment_5.entity.User;
import com.github.percivalgebashe.assignment_5.repository.RoleRepository;
import com.github.percivalgebashe.assignment_5.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<User> registerUser(User user) {
        if(user.getPassword() == null || !user.getPassword().isEmpty() &&
            user.getUsername() == null || !user.getUsername().isEmpty() &&
            user.getRoles() != null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                System.out.println("Saving user: " + user);

            Set<Role> existingRoles = user.getRoles().stream()
                    .map(role -> roleRepository.findByName(role.getName())
                            .orElseThrow(() -> new RuntimeException("Role not found: " + role)))
                    .collect(Collectors.toSet());

            user.setRoles(existingRoles);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }
}
