package by.com.clothify.security.service.restore;

import by.com.clothify.security.model.dto.enums.AuthType;
import by.com.clothify.security.model.dto.enums.restore.SendCodeError;
import by.com.clothify.security.model.dto.restore.CheckCodeRequestDto;
import by.com.clothify.security.model.dto.restore.CheckCodeResponseDto;
import by.com.clothify.security.model.dto.restore.SendCodeRequestDto;
import by.com.clothify.security.model.dto.restore.SendCodeResponseDto;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestoreService {

    private final UserRepository userRepository;
    private final CodeService codeService;
    private final EmailMessagesService emailMessagesService;

    public ResponseEntity<SendCodeResponseDto> sendCodeToEmail(SendCodeRequestDto request) {

        try {
            final String email = request.getEmail();
            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isEmpty()) {
                return createFailedResponse(SendCodeError.EMAIL_NOT_FOUND);
            }

            User user = optionalUser.get();

            Optional<ResponseEntity<SendCodeResponseDto>> optionalFailedResponse = requestValidation(user);
            if (optionalFailedResponse.isPresent()) {
                return optionalFailedResponse.get();
            }

            String code = codeService.createCode(email);

            boolean writeInfoResult = codeService.writeSendCodeRequestsInfo(email);
            if (!writeInfoResult) {
                return createFailedResponse(SendCodeError.EXCEEDED_SEND_ATTEMPTS_NUMBER);
            }

            boolean sendResult = emailMessagesService.sendCodeMessageToEmail(code, email);
            if (!sendResult) {
                return createFailedResponse(SendCodeError.MESSAGE_SEND_ERROR);
            }

            return ResponseEntity.ok().body(
                    SendCodeResponseDto.builder()
                            .success(true)
                            .build()
            );

        } catch (Exception e) {
            log.error("Failed send code to: {}", request.getEmail());
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Optional<ResponseEntity<SendCodeResponseDto>> requestValidation(User user) {

        if (AuthType.APPLE == user.getAuthType()) {
            return createOptionalFailedResponse(SendCodeError.EMAIL_OCCUPIED_BY_APPLE_AUTH);
        }

        if (AuthType.GOOGLE == user.getAuthType()) {
            return createOptionalFailedResponse(SendCodeError.EMAIL_OCCUPIED_BY_GOOGLE_AUTH);
        }

        return Optional.empty();
    }

    public ResponseEntity<CheckCodeResponseDto> checkCode(CheckCodeRequestDto request) {
        try {
            CheckCodeResponseDto checkResult = codeService.checkCode(request);

            if (checkResult.isSuccess()) {
                return ResponseEntity.ok(checkResult);
            } else {
                return ResponseEntity.status(checkResult.getErrorMessage().getStatus()).body(checkResult);
            }

        } catch (Exception e) {
            log.error("Failed check code for: {}", request.getEmail());
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Optional<ResponseEntity<SendCodeResponseDto>> createOptionalFailedResponse(SendCodeError error) {
        return Optional.of(createFailedResponse(error));
    }

    private ResponseEntity<SendCodeResponseDto> createFailedResponse(SendCodeError error) {
        return ResponseEntity.status(error.getStatus())
                .body(
                        SendCodeResponseDto.builder()
                                .success(false)
                                .errorMessage(error)
                                .build()
                );
    }
}
