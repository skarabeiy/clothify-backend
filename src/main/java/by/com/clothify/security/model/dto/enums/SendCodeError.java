package by.com.clothify.security.model.dto.enums;

public enum SendCodeError {
    EMAIL_NOT_FOUND,
    EMAIL_OCCUPIED_BY_APPLE_AUTH,
    EMAIL_OCCUPIED_BY_GOOGLE_AUTH,
    EXCEEDED_SEND_ATTEMPTS_NUMBER
}
