package by.com.clothify.security.controller;

import by.com.clothify.security.model.dto.AuthRequestDto;
import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.service.AuthenticateService;
import by.com.clothify.security.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Operations for authorization")
public class AuthController {

    private final RegisterService registerService;
    private final AuthenticateService authenticateService;

    @PostMapping("/register")
    @Operation(
            summary = "User registration",
            description = "Return tokens if successful, and an error if not"
    )
    public AuthResponseDto register(@RequestBody RegisterRequestDto request) {
        return registerService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthResponseDto authenticate(@RequestBody AuthRequestDto request) {
        return authenticateService.authenticate(request);
    }
}
