package com.github.percivalgebashe.assignment_5.service;

import com.github.percivalgebashe.assignment_5.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    public Boolean validateToken(String token, User userDetails);
}
