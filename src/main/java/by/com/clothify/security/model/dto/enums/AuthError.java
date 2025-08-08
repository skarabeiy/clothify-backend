package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthError {
    AUTH_ERROR(HttpStatus.BAD_REQUEST, "Auth error"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Bearer authorization is required for this endpoint"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT token is invalid"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Access JWT token has expired. Please refresh or re-authenticate"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Refresh JWT token has expired. Please re-authenticate"),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "Email specified in JWT token was not found in the DB");

    private final HttpStatus status;

    private final String description;
}
