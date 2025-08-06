package by.com.clothify.account.controller;

import by.com.clothify.security.model.dto.UserInformationDto;
import by.com.clothify.account.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Account API", description = "Operations to manage account information")
public class AccountController {

    private final UserService userService;

    @PutMapping("/fill-information")
    @Operation(
            summary = "Fill user information",
            description = "Filling information such as username and etc., after registration",
            security = @SecurityRequirement(name = "Bearer")
    )
    public void fillInformation(@RequestBody UserInformationDto userInformationDto) {
        userService.fillInformation(userInformationDto);
    }
}
