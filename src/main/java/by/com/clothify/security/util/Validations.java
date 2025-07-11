package by.com.clothify.security.util;

import java.util.regex.Pattern;

public final class Validations {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static final String ALLOWED_SPECIAL = "!@#$%^&*";
    private static final String REGEX_DIGIT = ".*[0-9].*";
    private static final String REGEX_UPPER = ".*[A-Z].*";
    private static final String REGEX_LOWER = ".*[a-z].*";

    public static boolean isPasswordValid(String password) {

        if (password == null || password.isEmpty()) {
            return false;
        }

        if (password.length() < 8 || password.length() > 20) {
            return false;
        }

        String regexSpecial = ".*[" + Pattern.quote(ALLOWED_SPECIAL) + "].*";

        if (!password.matches(REGEX_DIGIT)) {
            return false;
        }
        if (!password.matches(REGEX_UPPER)) {
            return false;
        }
        if (!password.matches(REGEX_LOWER)) {
            return false;
        }
        if (!password.matches(regexSpecial)) {
            return false;
        }

        String validCharsRegex = "^[a-zA-Z0-9" + Pattern.quote(ALLOWED_SPECIAL) + "]+$";
        return password.matches(validCharsRegex);
    }
}
