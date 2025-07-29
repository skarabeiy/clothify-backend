package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RefreshRequestDto;
import by.com.clothify.security.model.dto.enums.AuthError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.com.clothify.security.service.AuthService.getFailedAuthResponseEntity;
import static by.com.clothify.security.service.AuthService.getOptionalFailedAuthResponseEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshService {

    private final JwtService jwtService;
    private final AuthService authService;

    public ResponseEntity<AuthResponseDto> refreshTokens(RefreshRequestDto request) {
        try {
            final String refreshToken = request.getRefreshToken();
            final String userEmail = jwtService.extractUsername(refreshToken);
            UserDetails userDetails = jwtService.getUserDetails(userEmail);

            Optional<ResponseEntity<AuthResponseDto>> optionalFailedAuthResponse = checkRefreshToken(refreshToken, userDetails);

            return optionalFailedAuthResponse.orElseGet(() ->
                    ResponseEntity.ok(authService.createAuthResponse(userDetails)));

        } catch (SignatureException se) {
            log.error(AuthError.INVALID_TOKEN.getDescription());
            log.error(se.getMessage(), se);
            return getFailedAuthResponseEntity(AuthError.INVALID_TOKEN.getStatus(),
                    AuthError.INVALID_TOKEN.toString());

        } catch (ExpiredJwtException eje) {
            return getFailedAuthResponseEntity(AuthError.REFRESH_TOKEN_EXPIRED.getStatus(),
                    AuthError.REFRESH_TOKEN_EXPIRED.toString());

        } catch (Exception e) {
            log.error("Failed refresh tokens");
            log.error(e.getMessage(), e);
            return getFailedAuthResponseEntity(AuthError.AUTH_ERROR.getStatus(), AuthError.AUTH_ERROR.toString());
        }
    }

    private Optional<ResponseEntity<AuthResponseDto>> checkRefreshToken(String refreshToken, UserDetails userDetails) {

        if (!jwtService.isTokenValid(refreshToken, JwtService.REFRESH_TOKEN_TYPE)) {

            if (jwtService.isTokenExpired(refreshToken)) {
                return getOptionalFailedAuthResponseEntity(AuthError.REFRESH_TOKEN_EXPIRED.getStatus(),
                        AuthError.REFRESH_TOKEN_EXPIRED.toString());
            }

            log.error("Invalid refresh token for user: {}", userDetails.getUsername());
            return getOptionalFailedAuthResponseEntity(AuthError.INVALID_TOKEN.getStatus(),
                    AuthError.INVALID_TOKEN.toString());
        }

        return Optional.empty();
    }
}
