package com.taskmanagementsystem.security.cookie;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;


@Setter
@RequiredArgsConstructor
@Component
public class TokenCookieSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    @Value("${jwt.access-token-name}")
    private String accessTokenName;
    @Value("${jwt.refresh-token-name}")
    private String refreshTokenName;

    private final TokenCookieFactory tokenCookieFactory;
    private final CookieService cookieService;

    @Override
    public void onAuthentication(Authentication authentication,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        if (authentication instanceof UsernamePasswordAuthenticationToken) {

            var accessToken = tokenCookieFactory.createAccessToken(authentication);
            var refreshToken = tokenCookieFactory.createRefreshToken(authentication);

            cookieService.addCookieAccessToken(response, accessTokenName, accessToken);
            cookieService.addCookieRefreshToken(response, refreshTokenName, refreshToken);
        }
    }
}
