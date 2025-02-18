package com.github.percivalgebashe.assignment_5.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class WebUtils {

    public String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) { // Assuming the cookie name is "jwt"
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
