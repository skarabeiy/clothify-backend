package by.com.clothify.profile.controller;

import by.com.clothify.profile.model.dto.UserProfileRequestDto;
import by.com.clothify.profile.model.dto.UserProfileResponseDto;
import by.com.clothify.profile.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Tag(name = "User Profile API", description = "Operations to manage user profile information")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    @Operation(
            summary = "Get user profile information",
            description = "Return user information such as name, birthday and etc.",
            security = @SecurityRequirement(name = "Bearer")
    )
    public UserProfileResponseDto getUserProfile() {
        return userProfileService.getUserProfile();
    }

    @PostMapping
    @Operation(
            summary = "Save user information",
            description = "Filling information such as name and etc., after registration",
            security = @SecurityRequirement(name = "Bearer")
    )
    public void saveUserProfile(@RequestBody UserProfileRequestDto userProfileRequestDto) {
        userProfileService.saveUserProfile(userProfileRequestDto);
    }
}
