package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthError {
    AUTH_ERROR("Auth error"),
    UNAUTHORIZED("Barer authorization is required for this endpoint"),
    INVALID_TOKEN("JWT token is invalid"),
    TOKEN_EXPIRED("JWT token has expired. Please refresh or re-authenticate");

    private final String description;
}
