package by.com.clothify.security.model.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AppleAuthError {
    EMAIL_OCCUPIED_BY_PASSWORD_AUTH(HttpStatus.CONFLICT),
    EMAIL_OCCUPIED_BY_GOOGLE_AUTH(HttpStatus.CONFLICT);

    private final HttpStatus status;
}
