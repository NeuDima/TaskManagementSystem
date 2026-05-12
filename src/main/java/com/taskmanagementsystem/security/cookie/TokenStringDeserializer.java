package com.taskmanagementsystem.security.cookie;

import com.taskmanagementsystem.security.dto.Token;

public interface TokenStringDeserializer {

    Token apply(String string);
}
