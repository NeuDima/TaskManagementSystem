package com.taskmanagementsystem.security.cookie;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TokenCookieAuthenticationConverter implements AuthenticationConverter {

    @Value("${jwt.access-token-name}")
    private String accessTokenName;

    @Qualifier("AccessTokenJwsStringDeserializer")
    private final TokenStringDeserializer tokenCookiesStringDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Stream.of(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(accessTokenName))
                    .findFirst()
                    .map(cookie -> {
                        var token = tokenCookiesStringDeserializer.apply(cookie.getValue());
                        return new PreAuthenticatedAuthenticationToken(token, cookie.getValue());
                    })
                    .orElse(null);
        }

        return null;
    }
}
