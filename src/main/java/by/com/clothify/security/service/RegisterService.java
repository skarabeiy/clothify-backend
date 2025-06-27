package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.model.dto.enums.RegisterError;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import by.com.clothify.security.util.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static by.com.clothify.security.service.AuthService.getFailedAuthResponse;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public AuthResponseDto register(RegisterRequestDto request) {

        Optional<AuthResponseDto> optionalFailedAuthResponse = registerRequestValidation(request);

        if (optionalFailedAuthResponse.isPresent()) {
            return optionalFailedAuthResponse.get();
        }

        User user = mapToUser(request);
        userRepository.save(user);

        return authService.createAuthResponse(user);
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
                .name(request.getName())
                .gender(request.getGender())
                .dateOfBirth(DateConverter.covertToLocaleDate(request.getDateOfBirth()))
                .build();
    }
}
