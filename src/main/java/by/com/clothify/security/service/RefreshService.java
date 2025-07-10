package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RefreshRequestDto;
import by.com.clothify.security.model.dto.enums.AuthError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.com.clothify.security.service.AuthService.getFailedAuthResponse;
import static by.com.clothify.security.service.AuthService.getOptionalFailedAuthResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshService {

    private final JwtService jwtService;
    private final AuthService authService;

    public AuthResponseDto refreshTokens(RefreshRequestDto request) {
        try {
            final String refreshToken = request.getRefreshToken();
            final String userEmail = jwtService.extractUsername(refreshToken);
            UserDetails userDetails = jwtService.getUserDetails(userEmail);

            Optional<AuthResponseDto> optionalFailedAuthResponse = checkRefreshToken(refreshToken, userDetails);

            return optionalFailedAuthResponse.orElseGet(() ->
                    authService.createAuthResponse(userDetails));

        } catch (SignatureException se) {
            log.error(AuthError.INVALID_TOKEN.getDescription());
            log.error(se.getMessage(), se);
            return getFailedAuthResponse(AuthError.INVALID_TOKEN.toString());

        } catch (ExpiredJwtException eje) {
            return getFailedAuthResponse(AuthError.REFRESH_TOKEN_EXPIRED.toString());

        } catch (Exception e) {
            log.error("Failed refresh tokens");
            log.error(e.getMessage(), e);
            return getFailedAuthResponse(AuthError.AUTH_ERROR.toString());
        }
    }

    private Optional<AuthResponseDto> checkRefreshToken(String refreshToken, UserDetails userDetails) {

        if (!jwtService.isTokenValid(refreshToken, JwtService.REFRESH_TOKEN_TYPE)) {

            if (jwtService.isTokenExpired(refreshToken)) {
                return getOptionalFailedAuthResponse(AuthError.REFRESH_TOKEN_EXPIRED.toString());
            }

            log.error("Invalid refresh token for user: {}", userDetails.getUsername());
            return getOptionalFailedAuthResponse(AuthError.INVALID_TOKEN.toString());
        }

        return Optional.empty();
    }
}
