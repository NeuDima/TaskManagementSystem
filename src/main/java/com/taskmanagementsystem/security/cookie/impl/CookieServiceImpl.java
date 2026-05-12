package com.taskmanagementsystem.security.cookie.impl;

import com.taskmanagementsystem.security.cookie.CookieService;
import com.taskmanagementsystem.security.cookie.TokenStringSerializer;
import com.taskmanagementsystem.security.dto.Token;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    @Qualifier("RefreshTokenJweStringSerializer")
    private final TokenStringSerializer RefreshTokenSerializer;

    @Qualifier("AccessTokenJwsStringSerializer")
    private final TokenStringSerializer AccessTokenSerializer;


    @Override
    public void addCookieAccessToken(HttpServletResponse response, String name, Token token) {
        var tokenString = AccessTokenSerializer.apply(token);
        addCookie(response, name, tokenString, token, "/");
    }

    @Override
    public void addCookieRefreshToken(HttpServletResponse response, String name, Token token) {
        var tokenString = RefreshTokenSerializer.apply(token);
        addCookie(response, name, tokenString, token, "/auth/refresh");
    }

    private void addCookie(HttpServletResponse response, String name, String tokenString, Token token, String path) {
        var cookie = new Cookie(name, tokenString);
        cookie.setPath(path);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(), token.expiresAt()));

        response.addCookie(cookie);
    }
}
