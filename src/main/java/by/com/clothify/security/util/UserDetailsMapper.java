package by.com.clothify.security.util;

import by.com.clothify.security.model.dto.UserDetailsDto;
import by.com.clothify.security.model.entity.User;

public final class UserDetailsMapper {

    public static UserDetailsDto mapToUserDetailsDto(User user) {
        return UserDetailsDto.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
