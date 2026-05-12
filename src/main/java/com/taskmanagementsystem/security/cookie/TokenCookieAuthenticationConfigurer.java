package com.taskmanagementsystem.security.cookie;

import com.taskmanagementsystem.security.db.entity.DeactivatedToken;
import com.taskmanagementsystem.security.dto.TokenUser;
import com.taskmanagementsystem.security.service.DeactivatedTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TokenCookieAuthenticationConfigurer
        extends AbstractHttpConfigurer<TokenCookieAuthenticationConfigurer, HttpSecurity> {

    private final AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService;
    private final TokenCookieAuthenticationConverter tokenCookieAuthenticationConverter;
    private final String accessTokenName;
    private final String refreshTokenName;
    private final DeactivatedTokenService deactivatedTokenService;

    public TokenCookieAuthenticationConfigurer(
            AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService,
            TokenCookieAuthenticationConverter tokenCookieAuthenticationConverter,
            @Value("${jwt.access-token-name}") String accessTokenName,
            @Value("${jwt.refresh-token-name}") String refreshTokenName,
            DeactivatedTokenService deactivatedTokenService) {
        this.authenticationUserDetailsService = authenticationUserDetailsService;
        this.tokenCookieAuthenticationConverter = tokenCookieAuthenticationConverter;
        this.accessTokenName = accessTokenName;
        this.refreshTokenName = refreshTokenName;
        this.deactivatedTokenService = deactivatedTokenService;
    }

    @Override
    public void init(HttpSecurity builder) {
        builder.logout(logout -> logout
//                .addLogoutHandler(new CookieClearingLogoutHandler(accessTokenName))
                .addLogoutHandler(
                        (request, response, authentication) -> {
                            Cookie cookie = new Cookie(refreshTokenName, null);
                            cookie.setPath("/auth/refresh");
                            cookie.setMaxAge(0);
                            cookie.setHttpOnly(true);
                            cookie.setSecure(true);
                            response.addCookie(cookie);

                            cookie = new Cookie(accessTokenName, null);
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            cookie.setHttpOnly(true);
                            cookie.setSecure(true);
                            response.addCookie(cookie);
                        })
                .addLogoutHandler((
                        (request, response, authentication) -> {
                            if (authentication != null && authentication.getPrincipal() instanceof TokenUser user) {
                                DeactivatedToken deactivatedToken = new DeactivatedToken(
                                        user.getToken().id(), Date.from(user.getToken().expiresAt()));
                                deactivatedTokenService.create(deactivatedToken);

                                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                            }
                        }))
                .logoutSuccessHandler((
                        request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }))
        ;
    }

    @Override
    public void configure(HttpSecurity builder) {
        var cookieAuthenticationFilter = new AuthenticationFilter(
                builder.getSharedObject(AuthenticationManager.class),
                tokenCookieAuthenticationConverter);

        cookieAuthenticationFilter.setSuccessHandler((request, response, authentication) -> {
            var tokenUser = (TokenUser) authentication.getPrincipal();
            List<SimpleGrantedAuthority> authorities =
                    tokenUser.getToken()
                            .authorities()
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();
            Authentication newAuth =
                    new UsernamePasswordAuthenticationToken(
                            tokenUser,
                            null,
                            authorities
                    );
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(newAuth);

            SecurityContextHolder.setContext(context);
        });
        cookieAuthenticationFilter.setFailureHandler(
                new AuthenticationEntryPointFailureHandler(
                        new Http403ForbiddenEntryPoint()
                )
        );

        var authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService);

        builder.addFilterAfter(cookieAuthenticationFilter, CsrfFilter.class)
                .authenticationProvider(authenticationProvider);
    }
}