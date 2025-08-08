package by.com.clothify.security.service.restore;

import by.com.clothify.security.model.dto.AuthResponseDto;
import by.com.clothify.security.model.dto.enums.AuthType;
import by.com.clothify.security.model.dto.enums.restore.ChangeEmailError;
import by.com.clothify.security.model.dto.enums.restore.ChangePasswordError;
import by.com.clothify.security.model.dto.enums.restore.CheckCodeError;
import by.com.clothify.security.model.dto.enums.restore.SendCodeError;
import by.com.clothify.security.model.dto.restore.ChangeEmailRequestDto;
import by.com.clothify.security.model.dto.restore.ChangeEmailResponseDto;
import by.com.clothify.security.model.dto.restore.ChangePasswordRequestDto;
import by.com.clothify.security.model.dto.restore.ChangePasswordResponseDto;
import by.com.clothify.security.model.dto.restore.CheckCodeRequestDto;
import by.com.clothify.security.model.dto.restore.CheckCodeResponseDto;
import by.com.clothify.security.model.dto.restore.SendCodeRequestDto;
import by.com.clothify.security.model.dto.restore.SendCodeResponseDto;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import by.com.clothify.security.service.AuthService;
import by.com.clothify.security.util.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestoreService {

    @Value("${restore.code-expiration-time}")
    private Long codeExpirationInMinutes;

    @Value("${restore.code-retry-time}")
    private Long sendCodeRetryTime;

    @Value("${restore.code-attempts-number}")
    private Integer attemptsNumber;

    @Value("${restore.code-max-requests-number}")
    private Integer maxRequestsNumber;

    @Value("${restore.code-lock-time}")
    private Long lockTime;

    private final UserRepository userRepository;
    private final CodeService codeService;
    private final EmailMessagesService emailMessagesService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Transactional
    public ResponseEntity<SendCodeResponseDto> sendCodeToEmail(SendCodeRequestDto request) {

        try {
            final String email = request.getEmail();
            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isEmpty()) {
                return createFailedSendCodeResponse(SendCodeError.EMAIL_NOT_FOUND);
            }

            User user = optionalUser.get();

            Optional<ResponseEntity<SendCodeResponseDto>> optionalFailedResponse = requestValidation(user);
            if (optionalFailedResponse.isPresent()) {
                return optionalFailedResponse.get();
            }

            String code = codeService.createCode(email);

            boolean writeInfoResult = codeService.writeSendCodeRequestsInfo(email);
            if (!writeInfoResult) {
                return createFailedSendCodeResponse(SendCodeError.EXCEEDED_SEND_ATTEMPTS_NUMBER);
            }

            boolean sendResult = emailMessagesService.sendCodeMessageToEmail(email, code, codeExpirationInMinutes);
            if (!sendResult) {
                return createFailedSendCodeResponse(SendCodeError.MESSAGE_SEND_ERROR);
            }

            SendCodeResponseDto.InfoDto info = SendCodeResponseDto.InfoDto.builder()
                    .codeExpirationInMinutes(codeExpirationInMinutes)
                    .sendCodeRetryTimeInMinutes(sendCodeRetryTime)
                    .enterAttemptsNumber(attemptsNumber)
                    .sendCodeRequestMaxNumber(maxRequestsNumber)
                    .requestLockTimeInMinutes(lockTime)
                    .build();

            return ResponseEntity.ok().body(
                    SendCodeResponseDto.builder()
                            .success(true)
                            .info(info)
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
            return createOptionalFailedSendCodeResponse(SendCodeError.EMAIL_OCCUPIED_BY_APPLE_AUTH);
        }

        if (AuthType.GOOGLE == user.getAuthType()) {
            return createOptionalFailedSendCodeResponse(SendCodeError.EMAIL_OCCUPIED_BY_GOOGLE_AUTH);
        }

        return Optional.empty();
    }

    private Optional<ResponseEntity<SendCodeResponseDto>> createOptionalFailedSendCodeResponse(SendCodeError error) {
        return Optional.of(createFailedSendCodeResponse(error));
    }

    private ResponseEntity<SendCodeResponseDto> createFailedSendCodeResponse(SendCodeError error) {
        return ResponseEntity.status(error.getStatus())
                .body(
                        SendCodeResponseDto.builder()
                                .success(false)
                                .errorMessage(error)
                                .build()
                );
    }

    public ResponseEntity<CheckCodeResponseDto> checkCode(CheckCodeRequestDto request) {
        try {
            CheckCodeResponseDto checkResult = codeService.checkCode(request, true);

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

    @Transactional
    public ResponseEntity<ChangePasswordResponseDto> changePassword(ChangePasswordRequestDto request) {
        try {
            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

            if (optionalUser.isEmpty()) {
                log.error("Failed find email: {} for change password", request.getEmail());
                return createFailedChangePasswordResponse(ChangePasswordError.EMAIL_NOT_FOUND);
            }

            Optional<ResponseEntity<ChangePasswordResponseDto>> optionalFailedResponse
                    = changePasswordRequestValidation(request);

            if (optionalFailedResponse.isPresent()) {
                return optionalFailedResponse.get();
            }

            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);

            log.info("User: {} changed his password", request.getEmail());

            return ResponseEntity.ok(
                    ChangePasswordResponseDto.builder()
                            .success(true)
                            .build()
            );

        } catch (Exception e) {
            log.error("Failed change password for: {}", request.getEmail());
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Optional<ResponseEntity<ChangePasswordResponseDto>> changePasswordRequestValidation(
            ChangePasswordRequestDto request) {

        if (Validations.isPasswordNotValid(request.getNewPassword())) {
            return createOptionalFailedChangePasswordResponse(ChangePasswordError.PASSWORD_NOT_VALID);
        }

        if (!request.getNewPassword().equals(request.getNewPasswordConfirmation())) {
            return createOptionalFailedChangePasswordResponse(ChangePasswordError.PASSWORDS_NOT_MATCH);
        }

        CheckCodeRequestDto checkCodeRequest = CheckCodeRequestDto.builder()
                .email(request.getEmail())
                .code(request.getCode())
                .build();
        CheckCodeResponseDto checkResult = codeService.checkCode(checkCodeRequest, false);

        if (!checkResult.isSuccess()) {
            return createOptionalFailedChangePasswordResponse(mapToChangePasswordError(checkResult.getErrorMessage()));
        }

        return Optional.empty();
    }

    private Optional<ResponseEntity<ChangePasswordResponseDto>> createOptionalFailedChangePasswordResponse(
            ChangePasswordError error) {
        return Optional.of(createFailedChangePasswordResponse(error));
    }

    private ResponseEntity<ChangePasswordResponseDto> createFailedChangePasswordResponse(ChangePasswordError error) {
        return ResponseEntity.status(error.getStatus())
                .body(
                        ChangePasswordResponseDto.builder()
                                .success(false)
                                .errorMessage(error)
                                .build()
                );
    }

    private ChangePasswordError mapToChangePasswordError(CheckCodeError checkCodeError) {

        return switch (checkCodeError) {
            case CODE_FOR_THIS_EMAIL_NOT_FOUND -> ChangePasswordError.CODE_FOR_THIS_EMAIL_NOT_FOUND;
            case INCORRECT_CODE -> ChangePasswordError.INCORRECT_CODE;
            case CODE_EXPIRED -> ChangePasswordError.CODE_EXPIRED;
            case ATTEMPTS_EXCEEDED -> ChangePasswordError.ATTEMPTS_EXCEEDED;
        };
    }

    @Transactional
    public ResponseEntity<ChangeEmailResponseDto> changeEmail(ChangeEmailRequestDto request) {
        try {
            Optional<User> optionalUser = userRepository.findByEmail(request.getOldEmail());

            if (optionalUser.isEmpty()) {
                log.error("Failed find email: {} for change email", request.getOldEmail());
                return createFailedChangeEmailResponse(ChangeEmailError.OLD_EMAIL_NOT_FOUND);
            }

            Optional<ResponseEntity<ChangeEmailResponseDto>> optionalFailedResponse
                    = changeEmailRequestValidation(request);

            if (optionalFailedResponse.isPresent()) {
                return optionalFailedResponse.get();
            }

            User user = optionalUser.get();
            user.setEmail(request.getNewEmail());
            userRepository.save(user);

            log.info("User: {} changed his email", request.getOldEmail());

            AuthResponseDto authResponseDto = authService.createAuthResponseFromUser(user);

            return ResponseEntity.ok(
                    ChangeEmailResponseDto.builder()
                            .success(true)
                            .accessToken(authResponseDto.getAccessToken())
                            .refreshToken(authResponseDto.getRefreshToken())
                            .build()
            );

        } catch (Exception e) {
            log.error("Failed change email for: {}", request.getOldEmail());
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Optional<ResponseEntity<ChangeEmailResponseDto>> changeEmailRequestValidation(
            ChangeEmailRequestDto request) {

        if (!Validations.isEmailValid(request.getNewEmail())) {
            return createOptionalFailedChangeEmailResponse(ChangeEmailError.NEW_EMAIL_NOT_VALID);
        }

        if (userRepository.existsByEmail(request.getNewEmail())) {
            return createOptionalFailedChangeEmailResponse(ChangeEmailError.NEW_EMAIL_OCCUPIED);
        }

        CheckCodeRequestDto checkCodeRequest = CheckCodeRequestDto.builder()
                .email(request.getOldEmail())
                .code(request.getCode())
                .build();
        CheckCodeResponseDto checkResult = codeService.checkCode(checkCodeRequest, false);

        if (!checkResult.isSuccess()) {
            return createOptionalFailedChangeEmailResponse(mapToChangeEmailError(checkResult.getErrorMessage()));
        }

        return Optional.empty();
    }

    private Optional<ResponseEntity<ChangeEmailResponseDto>> createOptionalFailedChangeEmailResponse(
            ChangeEmailError error) {
        return Optional.of(createFailedChangeEmailResponse(error));
    }

    private ResponseEntity<ChangeEmailResponseDto> createFailedChangeEmailResponse(ChangeEmailError error) {
        return ResponseEntity.status(error.getStatus())
                .body(
                        ChangeEmailResponseDto.builder()
                                .success(false)
                                .errorMessage(error)
                                .build()
                );
    }

    private ChangeEmailError mapToChangeEmailError(CheckCodeError checkCodeError) {

        return switch (checkCodeError) {
            case CODE_FOR_THIS_EMAIL_NOT_FOUND -> ChangeEmailError.CODE_FOR_THIS_EMAIL_NOT_FOUND;
            case INCORRECT_CODE -> ChangeEmailError.INCORRECT_CODE;
            case CODE_EXPIRED -> ChangeEmailError.CODE_EXPIRED;
            case ATTEMPTS_EXCEEDED -> ChangeEmailError.ATTEMPTS_EXCEEDED;
        };
    }
}
