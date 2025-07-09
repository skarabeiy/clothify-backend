package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthError {
    AUTH_ERROR("Auth error"),
    UNAUTHORIZED("Bearer authorization is required for this endpoint"),
    INVALID_TOKEN("JWT token is invalid"),
    ACCESS_TOKEN_EXPIRED("Access JWT token has expired. Please refresh or re-authenticate"),
    REFRESH_TOKEN_EXPIRED("Refresh JWT token has expired. Please re-authenticate");

    private final String description;
}
