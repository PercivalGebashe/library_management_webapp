package com.github.percivalgebashe.assignment_5.service;

import com.github.percivalgebashe.assignment_5.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService{
    public Optional<User> registerUser(User user);
}
