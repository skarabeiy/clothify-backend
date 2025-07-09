package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthError {
    AUTH_ERROR("Auth error"),
    UNAUTHORIZED("Bearer authorization is required for this endpoint"),
    INVALID_TOKEN("JWT token is invalid"),
    // TODO: подумать что будет, если у refresh токена тоже истек срок действия
    TOKEN_EXPIRED("JWT token has expired. Please refresh or re-authenticate");

    private final String description;
}
