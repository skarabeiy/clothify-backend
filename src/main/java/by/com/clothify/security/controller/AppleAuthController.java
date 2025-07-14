package by.com.clothify.security.controller;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.apple.AppleAuthRequestDto;
import by.com.clothify.security.service.apple.AppleAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/apple")
@RequiredArgsConstructor
@Tag(name = "Apple Auth API", description = "Operations for apple authorization")
public class AppleAuthController {

    private final AppleAuthService appleAuthService;

    @PostMapping("/signing")
    @Operation(
            summary = "User registration or authentication via apple login",
            description = "Registers the user if it is new. Return tokens if registration or authentication successful"
    )
    public AuthResponseDto appleSigning(@RequestBody AppleAuthRequestDto appleAuthRequestDto) {
        return appleAuthService.appleSigning(appleAuthRequestDto);
    }
}
