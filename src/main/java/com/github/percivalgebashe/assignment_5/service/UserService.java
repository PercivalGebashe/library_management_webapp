package com.github.percivalgebashe.assignment_5.service;

import com.github.percivalgebashe.assignment_5.dto.UserDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public interface UserService{
    void registerUser(UserDTO userDTO)  throws BadRequestException;
}