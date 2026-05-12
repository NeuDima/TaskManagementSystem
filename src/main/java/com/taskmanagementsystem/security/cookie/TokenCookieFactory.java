package com.taskmanagementsystem.security.cookie;

import com.taskmanagementsystem.security.dto.Token;
import org.springframework.security.core.Authentication;

public interface TokenCookieFactory {

    Token createAccessToken(Authentication authentication);

    Token createRefreshToken(Authentication authentication);
}
