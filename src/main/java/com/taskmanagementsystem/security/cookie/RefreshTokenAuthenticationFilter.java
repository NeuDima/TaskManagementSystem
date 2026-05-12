package com.taskmanagementsystem.security.cookie;

import com.taskmanagementsystem.security.cookie.impl.DefaultTokenCookieFactory;
import com.taskmanagementsystem.security.dto.TokenType;
import com.taskmanagementsystem.security.service.SecurityUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenAuthenticationFilter extends OncePerRequestFilter {

    @Qualifier("RefreshTokenJweStringDeserializer")
    private final TokenStringDeserializer deserializer;
    private final DefaultTokenCookieFactory tokenFactory;
    private final CookieService cookieService;
    private final SecurityUserService securityUserService;

    @Value("${jwt.access-token-name}")
    private String accessTokenName;

    @Value("${jwt.refresh-token-name}")
    private String refreshTokenName;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if (!"/auth/refresh".equals(request.getRequestURI())
            || !"POST".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        var cookies = Optional.ofNullable(request.getCookies())
                .orElse(new Cookie[0]);

        var refreshCookie = Arrays.stream(cookies)
                .filter(c -> refreshTokenName.equals(c.getName()))
                .findFirst()
                .orElse(null);

        if (refreshCookie == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var refreshToken = deserializer.apply(refreshCookie.getValue());

        if (refreshToken.type() != TokenType.REFRESH || refreshToken.expiresAt().isBefore(Instant.now())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // роли из БД
        List<String> roles = List.of(securityUserService.getRoleNameByEmail(refreshToken.subject()).name());

        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        var authentication = new UsernamePasswordAuthenticationToken(
                refreshToken.subject(),
                null,
                authorities
        );

        var newAccessToken = tokenFactory.createAccessToken(authentication);

        cookieService.addCookieAccessToken(response, accessTokenName, newAccessToken);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}