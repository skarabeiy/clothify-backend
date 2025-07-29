package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.CheckEmailRequestDto;
import by.com.clothify.security.model.dto.CheckEmailResponseDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.model.dto.enums.AuthError;
import by.com.clothify.security.model.dto.enums.AuthType;
import by.com.clothify.security.model.dto.enums.CheckEmailResult;
import by.com.clothify.security.model.dto.enums.RegisterError;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import by.com.clothify.security.util.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static by.com.clothify.security.service.AuthService.getFailedAuthResponse;
import static by.com.clothify.security.service.AuthService.getOptionalFailedAuthResponseEntity;
import static by.com.clothify.security.service.AuthService.logErrorByAuthType;
import static by.com.clothify.security.util.Validations.isEmailValid;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public ResponseEntity<CheckEmailResponseDto> checkEmail(CheckEmailRequestDto request) {
        try {
            CheckEmailResult result = CheckEmailResult.EMAIL_FREE;

            if (!isEmailValid(request.getEmail())) {
                result = CheckEmailResult.EMAIL_NOT_VALID;
            } else {
                Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

                if (optionalUser.isPresent()) {

                    AuthType authType = optionalUser.get().getAuthType();
                    logErrorByAuthType(request.getEmail(), AuthType.PASSWORD, authType);
                    result = getCheckEmailOccupiedResult(authType);
                }
            }

            return ResponseEntity.status(result.getStatus())
                    .body(
                            CheckEmailResponseDto.builder()
                                    .resultMessage(result)
                                    .build()
                    );

        } catch (Exception e) {
            log.error("Failed check email: {}", request.getEmail());
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private CheckEmailResult getCheckEmailOccupiedResult(AuthType authType) {
        return switch (authType) {
            case PASSWORD -> CheckEmailResult.EMAIL_OCCUPIED_BY_PASSWORD_AUTH;
            case APPLE -> CheckEmailResult.EMAIL_OCCUPIED_BY_APPLE_AUTH;
            case GOOGLE -> CheckEmailResult.EMAIL_OCCUPIED_BY_GOOGLE_AUTH;
        };
    }

    public ResponseEntity<AuthResponseDto> register(RegisterRequestDto request) {
        try {
            Optional<ResponseEntity<AuthResponseDto>> optionalFailedAuthResponse = registerRequestValidation(request);

            if (optionalFailedAuthResponse.isPresent()) {
                return optionalFailedAuthResponse.get();
            }

            User user = mapToUser(request);
            userRepository.save(user);

            log.info("New user: {} registration", user.getEmail());

            return ResponseEntity.ok(authService.createAuthResponseFromUser(user));

        } catch (Exception e) {
            log.error("Failed register user: {}", request.getEmail());
            log.error(e.getMessage(), e);
            return ResponseEntity.status(AuthError.AUTH_ERROR.getStatus())
                    .body(getFailedAuthResponse(AuthError.AUTH_ERROR.toString()));
        }
    }

    // TODO: Обновить документацию
    private Optional<ResponseEntity<AuthResponseDto>> registerRequestValidation(RegisterRequestDto request) {

        if (!isEmailValid(request.getEmail())) {
            return getOptionalFailedAuthResponseEntity(RegisterError.EMAIL_NOT_VALID.getStatus(),
                    RegisterError.EMAIL_NOT_VALID.toString());
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {

            AuthType authType = optionalUser.get().getAuthType();
            RegisterError registerError = getRegisterOccupiedError(authType);
            logErrorByAuthType(request.getEmail(), AuthType.PASSWORD, authType);
            return getOptionalFailedAuthResponseEntity(registerError.getStatus(), registerError.toString());
        }

        if (!Validations.isPasswordValid(request.getPassword())) {
            return getOptionalFailedAuthResponseEntity(RegisterError.PASSWORD_NOT_VALID.getStatus(),
                    RegisterError.PASSWORD_NOT_VALID.toString());
        }

        if (isPasswordsNotMatch(request)) {
            return getOptionalFailedAuthResponseEntity(RegisterError.PASSWORDS_NOT_MATCH.getStatus(),
                    RegisterError.PASSWORDS_NOT_MATCH.toString());
        }

        return Optional.empty();
    }

    private RegisterError getRegisterOccupiedError(AuthType authType) {
        return switch (authType) {
            case PASSWORD -> RegisterError.EMAIL_OCCUPIED_BY_PASSWORD_AUTH;
            case APPLE -> RegisterError.EMAIL_OCCUPIED_BY_APPLE_AUTH;
            case GOOGLE -> RegisterError.EMAIL_OCCUPIED_BY_GOOGLE_AUTH;
        };
    }

    private static boolean isPasswordsNotMatch(RegisterRequestDto request) {
        return !request.getPassword().equals(request.getPasswordConfirmation());
    }

    private User mapToUser(RegisterRequestDto request) {
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .authType(AuthType.PASSWORD)
                .build();
    }
}
