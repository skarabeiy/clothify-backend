package by.com.clothify.security.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateConverter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDate covertToLocaleDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }
}
