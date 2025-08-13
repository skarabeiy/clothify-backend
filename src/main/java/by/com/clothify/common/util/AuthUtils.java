package by.com.clothify.common.util;

import by.com.clothify.security.model.dto.UserDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public final class AuthUtils {

    public static String getEmail() {
        try {
            return getUserDetails().getUsername();

        } catch (Exception e) {
            log.error("Failed get username from JWT token");
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static UserDetailsDto getUserDetails() {
        return (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
