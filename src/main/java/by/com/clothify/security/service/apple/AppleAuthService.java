package by.com.clothify.security.service.apple;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.apple.AppleAuthRequestDto;
import by.com.clothify.security.model.dto.enums.AppleAuthError;
import by.com.clothify.security.model.dto.enums.AuthType;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.com.clothify.security.service.AuthService.getOptionalFailedAuthResponse;
import static by.com.clothify.security.service.AuthService.logErrorByAuthType;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleAuthService {

    private final UserRepository userRepository;

    public AuthResponseDto appleSigning(AppleAuthRequestDto request) {

        String email = "";
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {

            Optional<AuthResponseDto> optionalFailedAuthResponse = requestValidation(optionalUser.get());

            if (optionalFailedAuthResponse.isPresent()) {
                return optionalFailedAuthResponse.get();
            }

        }

        return null;
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
}
