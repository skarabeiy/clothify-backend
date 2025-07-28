package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SendCodeError {
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND),
    EMAIL_OCCUPIED_BY_APPLE_AUTH(HttpStatus.CONFLICT),
    EMAIL_OCCUPIED_BY_GOOGLE_AUTH(HttpStatus.CONFLICT),
    EXCEEDED_SEND_ATTEMPTS_NUMBER(HttpStatus.TOO_MANY_REQUESTS),
    MESSAGE_SEND_ERROR(HttpStatus.BAD_GATEWAY);

    private final HttpStatus status;
}
