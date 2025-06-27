package by.com.clothify.security.service;


import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.UserDetailsDto;
import by.com.clothify.security.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static by.com.clothify.security.util.UserDetailsMapper.mapToUserDetailsDto;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    public AuthResponseDto createAuthResponse(User user) {
        UserDetailsDto userDetailsDto = mapToUserDetailsDto(user);

        String jwtAccessToken = jwtService.generateAccessToken(userDetailsDto);
        String jwtRefreshToken = jwtService.generateRefreshToken(userDetailsDto);

        return AuthResponseDto.builder()
                .success(true)
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

    public static AuthResponseDto getFailedAuthResponse(String errorMessage) {
        return AuthResponseDto.builder()
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }
}
