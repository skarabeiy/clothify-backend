package by.com.clothify.security.service;


import by.com.clothify.security.model.dto.AuthErrorDto;
import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.UserDetailsDto;
import by.com.clothify.security.model.dto.enums.AuthError;
import by.com.clothify.security.model.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static by.com.clothify.security.util.UserDetailsMapper.mapToUserDetailsDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

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

    public static Optional<AuthResponseDto> getOptionalFailedAuthResponse(String errorMessage) {
        return Optional.of(getFailedAuthResponse(errorMessage));
    }

    public static AuthResponseDto getFailedAuthResponse(String errorMessage) {
        return AuthResponseDto.builder()
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }

    public static void sendError(HttpServletResponse response, HttpStatus httpStatus, AuthError authError) {
        try {
            response.setContentType("application/json");
            response.setStatus(httpStatus.value());
            response.getWriter().write(getErrorBody(authError));
        } catch (IOException e) {
            log.error("Failed send auth error: {}", authError);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static String getErrorBody(AuthError authError) {
        try {
            AuthErrorDto authErrorDto = AuthErrorDto.builder()
                    .errorType(authError)
                    .errorDescription(authError.getDescription())
                    .build();

            return MAPPER.writeValueAsString(authErrorDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
