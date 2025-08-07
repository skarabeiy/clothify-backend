package by.com.clothify.profile.controller;

import by.com.clothify.profile.model.dto.UserProfileDto;
import by.com.clothify.profile.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "User Profile API", description = "Operations to manage user profile information")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/save")
    @Operation(
            summary = "Fill user information",
            description = "Filling information such as username and etc., after registration",
            security = @SecurityRequirement(name = "Bearer")
    )
    public void saveUserProfile(@RequestBody UserProfileDto userProfileDto) {
        userProfileService.saveUserProfile(userProfileDto);
    }
}
