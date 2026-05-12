package com.taskmanagementsystem.security;

import com.taskmanagementsystem.security.cookie.RefreshTokenAuthenticationFilter;
import com.taskmanagementsystem.security.cookie.TokenCookieAuthenticationConfigurer;
import com.taskmanagementsystem.security.csrf.GetCsrfTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final SessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy;
    private final RefreshTokenAuthenticationFilter refreshTokenAuthenticationFilter;
    private final TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer;
    private final GetCsrfTokenFilter getCsrfTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        http
                .formLogin(Customizer.withDefaults())
                .addFilterAfter(getCsrfTokenFilter, ExceptionTranslationFilter.class)
                .addFilterBefore(refreshTokenAuthenticationFilter, AuthenticationFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/api/tasks/user").hasRole("USER")
                                .requestMatchers("/api/tasks/admin").hasRole("ADMIN")
                                .requestMatchers("/error", "/index.html").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionAuthenticationStrategy(tokenCookieSessionAuthenticationStrategy))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/refresh", "/logout")
                        .csrfTokenRepository(new CookieCsrfTokenRepository())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(
                                (authentication,
                                 request,
                                 response) -> {
                                })
                );

        http.with(
                tokenCookieAuthenticationConfigurer,
                Customizer.withDefaults()
        );

        return http.build();
    }
}