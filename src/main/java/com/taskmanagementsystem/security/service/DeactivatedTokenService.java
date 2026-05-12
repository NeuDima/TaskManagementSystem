package com.taskmanagementsystem.security.service;

import com.taskmanagementsystem.security.db.entity.DeactivatedToken;
import com.taskmanagementsystem.security.dto.Token;

public interface DeactivatedTokenService {

    void create(DeactivatedToken deactivatedToken);

    boolean isTokenValid(Token token);
}
