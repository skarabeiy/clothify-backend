package by.com.clothify.security.service.apple;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.apple.AppleAuthRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppleAuthService {

    public AuthResponseDto appleSigning(AppleAuthRequestDto appleAuthRequestDto) {
        log.info(appleAuthRequestDto.getAppleAuthorizationCode());
        return null;
    }
}
