package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.model.dto.enums.AuthError;
import by.com.clothify.security.model.dto.enums.RegisterError;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static by.com.clothify.security.service.AuthService.getFailedAuthResponse;
import static by.com.clothify.security.service.AuthService.getOptionalFailedAuthResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public AuthResponseDto register(RegisterRequestDto request) {
        try {
            Optional<AuthResponseDto> optionalFailedAuthResponse = registerRequestValidation(request);

            if (optionalFailedAuthResponse.isPresent()) {
                return optionalFailedAuthResponse.get();
            }

            User user = mapToUser(request);
            userRepository.save(user);

            log.info("New user: {} registration", user.getEmail());

            return authService.createAuthResponse(user);
        } catch (Exception e) {
            log.error("Failed register user: {}", request.getEmail());
            log.error(e.getMessage(), e);
            return getFailedAuthResponse(AuthError.AUTH_ERROR.toString());
        }
    }

    private Optional<AuthResponseDto> registerRequestValidation(RegisterRequestDto request) {
        if (isPasswordsNotMatch(request)) {
            return getOptionalFailedAuthResponse(RegisterError.PASSWORDS_NOT_MATCH.toString());
        }

        if (isPasswordNotValid(request.getPassword())) {
            return getOptionalFailedAuthResponse(RegisterError.PASSWORD_NOT_VALID.toString());
        }

        if (isEmailNotValid(request.getEmail())) {
            return getOptionalFailedAuthResponse(RegisterError.EMAIL_NOT_VALID.toString());
        }

        if (isEmailOccupied(request.getEmail())) {
            return getOptionalFailedAuthResponse(RegisterError.EMAIL_OCCUPIED.toString());
        }

        return Optional.empty();
    }

    private static boolean isPasswordsNotMatch(RegisterRequestDto request) {
        return !request.getPassword().equals(request.getPasswordConfirmation());
    }

    private static boolean isPasswordNotValid(String password) {
        return false;
    }

    private static boolean isEmailNotValid(String email) {
        return false;
    }

    private boolean isEmailOccupied(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private User mapToUser(RegisterRequestDto request) {
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();
    }
}
