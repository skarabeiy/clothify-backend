package by.com.clothify.security.util;

import by.com.clothify.security.model.dto.UserDetailsDto;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthUtils {

    public static String getEmail() {
        return getUserDetails().getUsername();
    }

    public static UserDetailsDto getUserDetails() {
        return (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
