package com.taskmanagementsystem.security.cookie;

import com.taskmanagementsystem.security.dto.Token;

public interface TokenStringSerializer {

    String apply(Token token);
}
