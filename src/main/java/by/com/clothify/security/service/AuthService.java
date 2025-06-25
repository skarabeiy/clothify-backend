package by.com.clothify.security.service;


import by.com.clothify.security.model.dto.AuthRequestDto;
import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.model.dto.UserDetailsDto;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static by.com.clothify.security.util.UserDetailsMapper.mapToUserDetailsDto;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequestDto request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        UserDetailsDto userDetailsDto = mapToUserDetailsDto(user);

        String jwtToken = jwtService.generateToken(userDetailsDto);
        return AuthResponseDto.builder()
                .success(true)
                .accessToken(jwtToken)
                .build();
    }

    public AuthResponseDto authenticate(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        UserDetailsDto userDetailsDto = mapToUserDetailsDto(user);

        String jwtToken = jwtService.generateToken(userDetailsDto);
        return AuthResponseDto.builder()
                .success(true)
                .accessToken(jwtToken)
                .build();
    }
}
