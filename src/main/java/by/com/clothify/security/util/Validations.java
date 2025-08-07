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

    private static final String REGEX_DIGIT = ".*[0-9].*";
    private static final String REGEX_LETTER = ".*[a-zA-Z].*";

    public static boolean isPasswordNotValid(String password) {
        if (password == null || password.isEmpty()) {
            return true;
        }

        if (password.length() < 8 || password.length() > 20) {
            return true;
        }

        if (!password.matches(REGEX_DIGIT)) {
            return true;
        }

        return !password.matches(REGEX_LETTER);
    }
}
