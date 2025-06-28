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

    private static Optional<AuthResponseDto> registerRequestValidation(RegisterRequestDto request) {
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            return Optional.of(getFailedAuthResponse(RegisterError.PASSWORDS_NOT_MATCH.toString()));
        }

        return Optional.empty();
    }

    private User mapToUser(RegisterRequestDto request) {
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();
    }
}
