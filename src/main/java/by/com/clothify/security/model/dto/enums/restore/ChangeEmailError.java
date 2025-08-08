package by.com.clothify.security.model.dto.enums.restore;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChangeEmailError {
    OLD_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND),
    NEW_EMAIL_OCCUPIED(HttpStatus.CONFLICT),
    NEW_EMAIL_NOT_VALID(HttpStatus.BAD_REQUEST),

    CODE_FOR_THIS_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND),
    INCORRECT_CODE(HttpStatus.BAD_REQUEST),
    CODE_EXPIRED(HttpStatus.BAD_REQUEST),
    ATTEMPTS_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS);

    private final HttpStatus status;
}
