package by.com.clothify.security.controller;

import by.com.clothify.security.model.dto.UserInformationDto;
import by.com.clothify.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @PutMapping("/fill-information")
    public void fillInformation(@RequestBody UserInformationDto userInformationDto) {
        userService.fillInformation(userInformationDto);
    }
}
