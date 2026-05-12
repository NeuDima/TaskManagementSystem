package com.taskmanagementsystem.security.cookie.impl;

import com.taskmanagementsystem.security.cookie.TokenStringDeserializer;
import com.taskmanagementsystem.security.dto.Token;
import com.taskmanagementsystem.security.dto.TokenType;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;

@Slf4j
@Component("AccessTokenJwsStringDeserializer")
@RequiredArgsConstructor
public class AccessTokenJwsStringDeserializer implements TokenStringDeserializer {

    private final JWSVerifier jwsVerifier;

    @Override
    public Token apply(String string) {
        try {
            var signedJWT = SignedJWT.parse(string);
            if (signedJWT.verify(this.jwsVerifier)) {
                var claimsSet = signedJWT.getJWTClaimsSet();
                return new Token(UUID.fromString(claimsSet.getJWTID()), claimsSet.getSubject(),
                        claimsSet.getStringListClaim("authorities"),
                        claimsSet.getIssueTime().toInstant(),
                        claimsSet.getExpirationTime().toInstant(),
                        TokenType.ACCESS);
            }
        } catch (ParseException | JOSEException exception) {
            log.error(exception.getMessage(), exception);
        }
        return null;
    }
}