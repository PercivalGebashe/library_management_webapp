package com.github.percivalgebashe.assignment_5.service.impl;

import com.github.percivalgebashe.assignment_5.dto.UserDTO;
import com.github.percivalgebashe.assignment_5.entity.User;
import com.github.percivalgebashe.assignment_5.enums.Roles;
import com.github.percivalgebashe.assignment_5.repository.UserRepository;
import com.github.percivalgebashe.assignment_5.service.UserService;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(UserDTO userDTO) throws BadRequestException {
        validate(userDTO);

        if (!userRepository.existsUserByUsername(userDTO.getUsername())) {
            User user =  new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.saveAndFlush(user);
        }

    }

    private void validate(UserDTO userDTO) throws BadRequestException {
        if(userDTO.getPassword() == null || !userDTO.getPassword().isEmpty()) {
            throw new BadRequestException("User password cannot be null or empty");
        }
        if(userDTO.getUsername() == null || !userDTO.getUsername().isEmpty()) {
            throw new BadRequestException("Username cannot be null or empty");
        }
        if(userDTO.getRole() == null || !Roles.getRoles().contains(userDTO.getRole())) {
            throw new BadRequestException("Role cannot be null or empty");
        }
    }
}
