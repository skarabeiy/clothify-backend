package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthError {
    AUTH_ERROR("Auth error"),
    UNAUTHORIZED("Barer authorization is required for this endpoint");

    private final String description;
}
