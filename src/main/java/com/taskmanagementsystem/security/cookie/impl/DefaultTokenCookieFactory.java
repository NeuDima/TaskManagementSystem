package com.taskmanagementsystem.security.cookie.impl;

import com.taskmanagementsystem.security.cookie.TokenCookieFactory;
import com.taskmanagementsystem.security.dto.Token;
import com.taskmanagementsystem.security.dto.TokenType;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Setter
@Component
public class DefaultTokenCookieFactory implements TokenCookieFactory {

    private Duration accessTokenTtl = Duration.ofMinutes(15);
    private Duration refreshTokenTtl = Duration.ofDays(7);

    public Token createAccessToken(Authentication authentication) {
        var now = Instant.now();

        return new Token(
                UUID.randomUUID(),
                authentication.getName(),
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList(),
                now,
                now.plus(accessTokenTtl),
                TokenType.ACCESS
        );
    }

    public Token createRefreshToken(Authentication authentication) {
        var now = Instant.now();

        return new Token(
                UUID.randomUUID(),
                authentication.getName(),
                List.of(),
                now,
                now.plus(refreshTokenTtl),
                TokenType.REFRESH
        );
    }
}
