package by.com.clothify.security.controller;

import by.com.clothify.security.model.dto.AuthRequestDto;
import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.CheckEmailRequestDto;
import by.com.clothify.security.model.dto.CheckEmailResponseDto;
import by.com.clothify.security.model.dto.RefreshRequestDto;
import by.com.clothify.security.model.dto.RegisterRequestDto;
import by.com.clothify.security.service.AuthenticateService;
import by.com.clothify.security.service.RefreshService;
import by.com.clothify.security.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "Operations for authorization")
public class AuthController {

    private final RegisterService registerService;
    private final AuthenticateService authenticateService;
    private final RefreshService refreshService;

    // TODO: Удобная штука, но пока отложу - время + загромождение + актуальность
    @PostMapping("/check-email")
    @Operation(
            summary = "Check email availability",
            description = "Check email validation and availability",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Email is not valid",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "resultMessage": "EMAIL_NOT_VALID"
                                                    }
                                                    """
                                    )
                            )),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email has already been registered in another way",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "resultMessage": "EMAIL_OCCUPIED_BY_PASSWORD_AUTH"
                                                    }
                                                    """
                                    )
                            ))
            }
    )
    public ResponseEntity<CheckEmailResponseDto> checkEmail(@RequestBody CheckEmailRequestDto request) {
        return registerService.checkEmail(request);
    }

    @PostMapping("/register")
    @Operation(
            summary = "User registration",
            description = "Return tokens if successful, and an error if not"
    )
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        return registerService.register(request);
    }

    @PostMapping("/authenticate")
    @Operation(
            summary = "User authentication",
            description = "Return tokens if successful, and an error if not"
    )
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto request) {
        return authenticateService.authenticate(request);
    }

    @PostMapping("/refresh-tokens")
    @Operation(
            summary = "Refresh tokens",
            description = "If access token has expired, it issues a new pair of tokens for the refresh token"
    )
    public ResponseEntity<AuthResponseDto> refreshTokens(@RequestBody RefreshRequestDto request) {
        return refreshService.refreshTokens(request);
    }
}
