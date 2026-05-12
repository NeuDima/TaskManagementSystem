package com.taskmanagementsystem.security.service;

import com.taskmanagementsystem.security.dto.Token;
import com.taskmanagementsystem.security.dto.TokenType;
import com.taskmanagementsystem.security.dto.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationUserDetailsService
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final DeactivatedTokenService deactivatedTokenService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticationToken)
            throws UsernameNotFoundException {
        if (authenticationToken.getPrincipal() instanceof Token token) {
            if (token.type() != TokenType.ACCESS) {
                throw new UsernameNotFoundException("Only access token allowed");
            }

            boolean tokenValid = deactivatedTokenService.isTokenValid(token);

            return new TokenUser(
                    token.subject(),
                    "nopassword",
                    true,
                    true,
                    tokenValid,
                    true,
                    token.authorities().stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList(), token);
        }

        throw new UsernameNotFoundException("Principal must me of type Token");
    }
}