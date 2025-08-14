package by.com.clothify.common.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDate covertToLocaleDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    public static String convertToString(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return localDate.format(DATE_FORMATTER);
    }

    public static boolean isDateExpired(LocalDateTime date, Long expirationInSeconds) {
        if (date == null) {
            return true;
        }

        return Duration.between(date, LocalDateTime.now()).toSeconds() >= (expirationInSeconds);
    }
}
