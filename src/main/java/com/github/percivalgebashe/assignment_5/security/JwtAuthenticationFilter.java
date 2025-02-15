package com.github.percivalgebashe.assignment_5.security;

import com.github.percivalgebashe.assignment_5.service.AuthService;
import com.github.percivalgebashe.assignment_5.service.UserService;
import com.github.percivalgebashe.assignment_5.service.impl.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
//    private final String jwtToken;
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService, UserService userService, AuthService authService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authService = authService;
//        this.jwtToken = JwtUtil.generateToken();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationsHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if(null != authorizationsHeader && authorizationsHeader.startsWith("Bearer ")) {
            jwt = authorizationsHeader.substring(7);
//            username = s
        }
    }
}
