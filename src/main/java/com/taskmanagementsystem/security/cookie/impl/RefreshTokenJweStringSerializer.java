package com.taskmanagementsystem.security.cookie.impl;

import com.taskmanagementsystem.security.cookie.TokenStringSerializer;
import com.taskmanagementsystem.security.dto.Token;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component("RefreshTokenJweStringSerializer")
@RequiredArgsConstructor
public class RefreshTokenJweStringSerializer implements TokenStringSerializer {

    private final JWEEncrypter jweEncrypter;
    private final JWEAlgorithm jweAlgorithm;
    private final EncryptionMethod encryptionMethod;

    @Override
    public String apply(Token token) {
        var jweHeader = new JWEHeader.Builder(this.jweAlgorithm, this.encryptionMethod)
                .keyID(token.id().toString())
                .build();
        var claimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorities", token.authorities())
                .claim("type", token.type().name())
                .build();
        var encryptedJWT = new EncryptedJWT(jweHeader, claimsSet);
        try {
            encryptedJWT.encrypt(this.jweEncrypter);

            return encryptedJWT.serialize();
        } catch (JOSEException exception) {
            log.error(exception.getMessage(), exception);
        }

        return null;
    }
}