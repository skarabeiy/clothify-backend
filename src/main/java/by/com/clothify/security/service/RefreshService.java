package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RefreshRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshService {

    private final JwtService jwtService;
    private final AuthService authService;

    public AuthResponseDto refreshTokens(RefreshRequestDto request) {
        final String refreshToken = request.getRefreshToken();
        final String userEmail = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = jwtService.getUserDetails(userEmail);

        Optional<AuthResponseDto> optionalFailedAuthResponse = checkRefreshToken(refreshToken, userDetails);

        return optionalFailedAuthResponse.orElseGet(() ->
                authService.createAuthResponse(userDetails));
    }

    private Optional<AuthResponseDto> checkRefreshToken(String refreshToken, UserDetails userDetails) {

        return Optional.empty();
    }
}
