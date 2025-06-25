package by.com.clothify.security.service;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.model.dto.UserDetailsDto;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import by.com.clothify.security.util.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static by.com.clothify.security.util.UserDetailsMapper.mapToUserDetailsDto;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDto register(RegisterRequestDto request) {
        User user = mapToUser(request);
        userRepository.save(user);
        UserDetailsDto userDetailsDto = mapToUserDetailsDto(user);

        String jwtToken = jwtService.generateToken(userDetailsDto);
        return AuthResponseDto.builder()
                .success(true)
                .accessToken(jwtToken)
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
}
