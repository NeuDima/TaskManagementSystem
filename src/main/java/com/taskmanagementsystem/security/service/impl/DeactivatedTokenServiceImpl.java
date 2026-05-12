package com.taskmanagementsystem.security.service.impl;

import com.taskmanagementsystem.security.db.entity.DeactivatedToken;
import com.taskmanagementsystem.security.db.repository.DeactivatedTokenRepository;
import com.taskmanagementsystem.security.dto.Token;
import com.taskmanagementsystem.security.service.DeactivatedTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class DeactivatedTokenServiceImpl implements DeactivatedTokenService {

    private final DeactivatedTokenRepository deactivatedTokenRepository;

    @Override
    public void create(DeactivatedToken deactivatedToken) {
        deactivatedTokenRepository.save(deactivatedToken);
    }

    @Override
    public boolean isTokenValid(Token token) {
        return !deactivatedTokenRepository.existsById(token.id())
               && token.expiresAt().isAfter(Instant.now());
    }
}
