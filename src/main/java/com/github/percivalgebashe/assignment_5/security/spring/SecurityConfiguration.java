package com.github.percivalgebashe.assignment_5.security.spring;

import com.github.percivalgebashe.assignment_5.enums.Roles;
import com.github.percivalgebashe.assignment_5.security.jwt.JwtAuthenticationFilter;
import com.github.percivalgebashe.assignment_5.security.jwt.JwtUtil;
import com.github.percivalgebashe.assignment_5.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfiguration {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final WebUtils webUtils;

    @Autowired
    public SecurityConfiguration(JwtUtil jwtUtil, UserDetailsService userDetailsService, WebUtils webUtils) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.webUtils = webUtils;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable) // Disable form login for JWT authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/home",
                                "/css/**",
                                "/js/**",
                                "/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/library/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/api/v1/library/user/**").hasRole(Roles.USER.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsService, jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}