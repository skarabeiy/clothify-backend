package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticateError {
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED),
    EMAIL_OCCUPIED_BY_APPLE_AUTH(HttpStatus.CONFLICT),
    EMAIL_OCCUPIED_BY_GOOGLE_AUTH(HttpStatus.CONFLICT);

    private final HttpStatus status;
}
