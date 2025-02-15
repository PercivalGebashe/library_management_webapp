package com.github.percivalgebashe.assignment_5.security;

import com.github.percivalgebashe.assignment_5.entity.User;
import com.github.percivalgebashe.assignment_5.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = (1000 * 60 * 60);

    public static String generateToken(Authentication authentication, UserService userService) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getKey())
                .claim("usertype", "ADMIN" )
                .compact();
    }

    public static SecretKey getKey() {
        return Keys.hmacShaKeyFor("TEMP_PASSWORD".getBytes(StandardCharsets.UTF_8));
    }

    public static String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey())
                .compact();
    }


    public static Boolean validateToken(String token){
        String username = extractUser(token);
        Date expiration = extractExpiration(token);
        return username != null &&
                username.equals(extractUser(token)) &&
                expiration != null && expiration.before(new Date()); //currently checking against self
    }

    public static String extractUser(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(JwtUtil.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public static Date extractExpiration(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(JwtUtil.getKey(userDetails))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration();
    }




}
