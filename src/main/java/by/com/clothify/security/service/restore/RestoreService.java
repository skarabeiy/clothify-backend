package by.com.clothify.security.service.restore;

import by.com.clothify.security.model.dto.enums.AuthType;
import by.com.clothify.security.model.dto.enums.SendCodeError;
import by.com.clothify.security.model.dto.restore.SendCodeRequestDto;
import by.com.clothify.security.model.dto.restore.SendCodeResponseDto;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestoreService {

    private final UserRepository userRepository;
    private final CodeService codeService;
    private final EmailMessagesService emailMessagesService;

    public SendCodeResponseDto sendCodeToEmail(SendCodeRequestDto request) {

        final String email = request.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return createFailedResponse(SendCodeError.EMAIL_NOT_FOUND);
        }

        User user = optionalUser.get();

        Optional<SendCodeResponseDto> optionalFailedResponse = requestValidation(user);

        if (optionalFailedResponse.isPresent()) {
            return optionalFailedResponse.get();
        }

        String code = codeService.createCode(email);

        return null;
    }

    private Optional<SendCodeResponseDto> requestValidation(User user) {

        if (AuthType.APPLE == user.getAuthType()) {
            return createOptionalFailedResponse(SendCodeError.EMAIL_OCCUPIED_BY_APPLE_AUTH);
        }

        if (AuthType.GOOGLE == user.getAuthType()) {
            return createOptionalFailedResponse(SendCodeError.EMAIL_OCCUPIED_BY_GOOGLE_AUTH);
        }

        return Optional.empty();
    }

    private Optional<SendCodeResponseDto> createOptionalFailedResponse(SendCodeError error) {
        return Optional.of(createFailedResponse(error));
    }

    private SendCodeResponseDto createFailedResponse(SendCodeError error) {
        return SendCodeResponseDto.builder()
                .success(false)
                .errorMessage(error)
                .build();
    }
}
