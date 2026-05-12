package com.taskmanagementsystem.security.config;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import tools.jackson.databind.ObjectMapper;

import java.text.ParseException;

@Configuration
@RequiredArgsConstructor
public class SecurityBeanConfig {

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new CookieCsrfTokenRepository();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JWSAlgorithm jwsAlgorithm() {
        return JWSAlgorithm.HS256;
    }

    @Bean
    public EncryptionMethod encryptionMethod() {
        return EncryptionMethod.A128GCM;
    }

    @Bean
    public JWEAlgorithm jweAlgorithm() {
        return JWEAlgorithm.DIR;
    }

    @Bean
    public JWSSigner jwsSigner(@Value("${jwt.access-token-key}") String accessTokenKey)
            throws ParseException, KeyLengthException {
        return new MACSigner(OctetSequenceKey.parse(accessTokenKey));
    }

    @Bean
    public JWSVerifier jwsVerifier(@Value("${jwt.access-token-key}") String accessTokenKey)
            throws ParseException, JOSEException {
        return new MACVerifier(OctetSequenceKey.parse(accessTokenKey));
    }

    @Bean
    public JWEEncrypter jweEncrypter(@Value("${jwt.refresh-token-key}") String cookieTokenKey)
            throws ParseException, KeyLengthException {
        return new DirectEncrypter(OctetSequenceKey.parse(cookieTokenKey));
    }

    @Bean
    public JWEDecrypter jweDecrypter(@Value("${jwt.refresh-token-key}") String cookieTokenKey)
            throws ParseException, KeyLengthException {
        return new DirectDecrypter(OctetSequenceKey.parse(cookieTokenKey));
    }
}
