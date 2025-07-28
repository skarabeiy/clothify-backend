package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RegisterError {
    PASSWORDS_NOT_MATCH(HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST),
    EMAIL_NOT_VALID(HttpStatus.BAD_REQUEST),
    EMAIL_OCCUPIED_BY_PASSWORD_AUTH(HttpStatus.CONFLICT),
    EMAIL_OCCUPIED_BY_APPLE_AUTH(HttpStatus.CONFLICT),
    EMAIL_OCCUPIED_BY_GOOGLE_AUTH(HttpStatus.CONFLICT);

    private final HttpStatus status;
}
