package by.com.clothify.security.controller;

import by.com.clothify.security.model.dto.restore.SendCodeRequestDto;
import by.com.clothify.security.model.dto.restore.SendCodeResponseDto;
import by.com.clothify.security.service.restore.RestoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/restore")
@RequiredArgsConstructor
@Tag(name = "Restore API", description = "Operations for restore user data")
public class RestoreController {

    private final RestoreService restoreService;

    @PostMapping("/send-code")
    @Operation(
            summary = "Send code to user's email",
            description = "Send code to user's email for data restore"
    )
    public ResponseEntity<SendCodeResponseDto> sendCodeToEmail(@RequestBody SendCodeRequestDto request) {
        return restoreService.sendCodeToEmail(request);
    }
}
