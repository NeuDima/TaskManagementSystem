package com.taskmanagementsystem.security.cookie.impl;

import com.taskmanagementsystem.security.cookie.TokenStringDeserializer;
import com.taskmanagementsystem.security.dto.Token;
import com.taskmanagementsystem.security.dto.TokenType;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;

@Slf4j
@Component("RefreshTokenJweStringDeserializer")
@RequiredArgsConstructor
public class RefreshTokenJweStringDeserializer implements TokenStringDeserializer {

    private final JWEDecrypter jweDecrypter;

    @Override
    public Token apply(String string) {
        try {
            var encryptedJWT = EncryptedJWT.parse(string);
            encryptedJWT.decrypt(this.jweDecrypter);
            var claimsSet = encryptedJWT.getJWTClaimsSet();

            return new Token(
                    UUID.fromString(claimsSet.getJWTID()),
                    claimsSet.getSubject(),
                    claimsSet.getStringListClaim("authorities"),
                    claimsSet.getIssueTime().toInstant(),
                    claimsSet.getExpirationTime().toInstant(),
                    TokenType.valueOf(claimsSet.getStringClaim("type")));
        } catch (ParseException | JOSEException exception) {
            log.error(exception.getMessage(), exception);
        }

        return null;
    }
}