package com.github.percivalgebashe.assignment_5.service;

import com.github.percivalgebashe.assignment_5.entity.User;
import com.github.percivalgebashe.assignment_5.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> registerUser(User user) {
        if(user.getPassword() == null || user.getPassword().isEmpty() &&
            user.getUsername() == null || user.getUsername().isEmpty() &&
            user.getRoles() != null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }
}
