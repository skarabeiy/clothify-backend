package by.com.clothify.security.service.apple;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.apple.AppleAuthRequestDto;
import by.com.clothify.security.model.dto.apple.AppleResponseDto;
import by.com.clothify.security.model.dto.enums.AppleAuthError;
import by.com.clothify.security.model.dto.enums.AuthError;
import by.com.clothify.security.model.dto.enums.AuthType;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import by.com.clothify.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static by.com.clothify.security.service.AuthService.getFailedAuthResponse;
import static by.com.clothify.security.service.AuthService.getOptionalFailedAuthResponse;
import static by.com.clothify.security.service.AuthService.logErrorByAuthType;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleAuthService {

    private final AppleTokenService appleTokenService;
    private final UserRepository userRepository;
    private final AuthService authService;

    public AuthResponseDto appleSigning(AppleAuthRequestDto request) {

        try {
            String email = makeAppleRequestAndGetEmail(request);

            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user;

            if (optionalUser.isPresent()) {

                Optional<AuthResponseDto> optionalFailedAuthResponse = requestValidation(optionalUser.get());

                if (optionalFailedAuthResponse.isPresent()) {
                    return optionalFailedAuthResponse.get();
                }

                user = optionalUser.get();

            } else {
                user = registerNewUser(email);
            }

            return authService.createAuthResponseFromUser(user);

        } catch (Exception e) {
            log.error("Failed apple signing for: {}", request.getEmail());
            log.error(e.getMessage(), e);
            return getFailedAuthResponse(AuthError.AUTH_ERROR.toString());
        }
    }

    private String makeAppleRequestAndGetEmail(AppleAuthRequestDto request) {

        AppleResponseDto appleResponse = appleTokenService.makeTokenRequestToApple(request.getAppleAuthorizationCode());

        if (!appleResponse.isSuccess()) {
            throw new RuntimeException();

        } else {
            return appleResponse.getEmail().isPresent()
                    ? appleResponse.getEmail().get()
                    : request.getEmail();
        }
    }

    private Optional<AuthResponseDto> requestValidation(User user) {

        if (AuthType.PASSWORD == user.getAuthType()) {
            logErrorByAuthType(user.getEmail(), AuthType.APPLE, user.getAuthType());
            return getOptionalFailedAuthResponse(AppleAuthError.EMAIL_OCCUPIED_BY_PASSWORD_AUTH.toString());

        } else if (AuthType.GOOGLE == user.getAuthType()) {
            logErrorByAuthType(user.getEmail(), AuthType.APPLE, user.getAuthType());
            return getOptionalFailedAuthResponse(AppleAuthError.EMAIL_OCCUPIED_BY_GOOGLE_AUTH.toString());
        }

        return Optional.empty();
    }

    private User registerNewUser(String email) {

        User user = User.builder()
                .email(email)
                .createdAt(LocalDateTime.now())
                .authType(AuthType.APPLE)
                .build();

        userRepository.save(user);

        log.info("New user: {} registration", user.getEmail());
        return user;
    }
}
