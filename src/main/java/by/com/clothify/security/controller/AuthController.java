package by.com.clothify.security.controller;

import by.com.clothify.security.model.dto.AuthRequestDto;
import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.service.AuthService;
import by.com.clothify.security.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService registerService;
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody RegisterRequestDto request) {
        return registerService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthResponseDto authenticate(@RequestBody AuthRequestDto request) {
        return authService.authenticate(request);
    }
}
