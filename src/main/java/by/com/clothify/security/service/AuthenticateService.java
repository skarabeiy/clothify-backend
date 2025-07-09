package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthRequestDto;
import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.enums.AuthError;
import by.com.clothify.security.model.dto.enums.AuthenticateError;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.com.clothify.security.service.AuthService.getFailedAuthResponse;
import static by.com.clothify.security.service.AuthService.getOptionalFailedAuthResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthResponseDto authenticate(AuthRequestDto request) {
        try {
            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

            if (optionalUser.isEmpty()) {
                return getFailedAuthResponse(AuthenticateError.EMAIL_NOT_FOUND.toString());
            }

            Optional<AuthResponseDto> optionalFailedAuthResponse = authenticateCredentials(request);

            return optionalFailedAuthResponse.orElseGet(() ->
                    authService.createAuthResponseFromUser(optionalUser.get()));
        } catch (Exception e) {
            log.error("User authorization failed: {}", request.getEmail());
            log.error(e.getMessage(), e);
            return getFailedAuthResponse(AuthError.AUTH_ERROR.toString());
        }
    }

    private Optional<AuthResponseDto> authenticateCredentials(AuthRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException bce) {
            log.error("User: {} entered incorrect credentials", request.getEmail());
            return getOptionalFailedAuthResponse(AuthenticateError.BAD_CREDENTIALS.toString());
        }

        return Optional.empty();
    }
}
