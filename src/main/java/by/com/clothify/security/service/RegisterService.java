package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.model.dto.UserDetailsDto;
import by.com.clothify.security.model.dto.enums.RegisterError;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import by.com.clothify.security.util.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static by.com.clothify.security.util.UserDetailsMapper.mapToUserDetailsDto;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDto register(RegisterRequestDto request) {

        Optional<AuthResponseDto> optionalFailedAuthResponse = registerRequestValidation(request);

        if (optionalFailedAuthResponse.isPresent()) {
            return optionalFailedAuthResponse.get();
        }

        User user = mapToUser(request);
        userRepository.save(user);

        return createAuthResponse(user);
    }

    private Optional<AuthResponseDto> registerRequestValidation(RegisterRequestDto request) {
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            return Optional.of(getFailedAuthResponse(RegisterError.PASSWORDS_NOT_MATCH));
        }

        return Optional.empty();
    }

    private AuthResponseDto getFailedAuthResponse(RegisterError registerError) {
        return AuthResponseDto.builder()
                .success(false)
                .errorMessage(registerError.toString())
                .build();
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

    private AuthResponseDto createAuthResponse(User user) {
        UserDetailsDto userDetailsDto = mapToUserDetailsDto(user);

        String jwtAccessToken = jwtService.generateAccessToken(userDetailsDto);
        String jwtRefreshToken = jwtService.generateRefreshToken(userDetailsDto);

        return AuthResponseDto.builder()
                .success(true)
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }
}
