package com.taskmanagementsystem.security.cookie;

import com.taskmanagementsystem.security.dto.Token;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {

    void addCookieAccessToken(HttpServletResponse response, String name, Token token);

    void addCookieRefreshToken(HttpServletResponse response, String name, Token token);
}
