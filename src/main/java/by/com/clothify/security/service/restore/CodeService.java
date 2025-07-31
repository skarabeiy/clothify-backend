package by.com.clothify.security.service.restore;

import by.com.clothify.security.model.dto.enums.restore.CheckCodeError;
import by.com.clothify.security.model.dto.restore.CheckCodeRequestDto;
import by.com.clothify.security.model.dto.restore.CheckCodeResponseDto;
import by.com.clothify.security.model.entity.SendCodeRequest;
import by.com.clothify.security.model.entity.UserCode;
import by.com.clothify.security.repository.SendCodeRequestRepository;
import by.com.clothify.security.repository.UserCodeRepository;
import by.com.clothify.security.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

    @Value("${restore.code-expiration-time}")
    private Long codeExpirationInMinutes;

    @Value("${restore.code-attempts-number}")
    private int attemptsNumber;

    @Value("${restore.code-max-requests-number}")
    private int maxRequestsNumber;

    @Value("${restore.code-lock-time}")
    private Long lockTime;

    //    TODO: Лучше поменять на redis, но пока так
    private final UserCodeRepository userCodeRepository;
    private final SendCodeRequestRepository sendCodeRequestRepository;

    @Transactional
    public String createCode(String email) {

        try {
            String code = generateCode();
            saveCode(email, code);
            return code;

        } catch (Exception e) {
            log.error("Failed create code for: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static String generateCode() {
        return String.valueOf(
                ThreadLocalRandom.current().nextInt(100_000, 1_000_000)
        );
    }

    private void saveCode(String email, String code) {

        UserCode userCode;
        Optional<UserCode> optionalUserCode = userCodeRepository.findByEmail(email);

        if (optionalUserCode.isPresent()) {
            userCode = optionalUserCode.get();

            userCode.setCode(code);
            userCode.setCreatedAt(LocalDateTime.now());
            userCode.setAttempts(attemptsNumber);

        } else {
            userCode = UserCode.builder()
                    .email(email)
                    .code(code)
                    .createdAt(LocalDateTime.now())
                    .attempts(attemptsNumber)
                    .build();
        }

        userCodeRepository.save(userCode);
    }

    // TODO: стоит подумать над транзакшионалами
    @Transactional
    public boolean writeSendCodeRequestsInfo(String email) {

        try {
            Optional<SendCodeRequest> optionalRequestInfo = sendCodeRequestRepository.findByEmail(email);

            if (optionalRequestInfo.isPresent()) {

                SendCodeRequest requestInfo = optionalRequestInfo.get();

                if (requestInfo.getRequestsNumber() >= maxRequestsNumber) {
                    log.warn("User: {} exceeded the maximum number of code requests ({})", email, maxRequestsNumber);
                    return false;
                }

                requestInfo.setRequestsNumber(requestInfo.getRequestsNumber() + 1);
                sendCodeRequestRepository.save(requestInfo);

            } else {
                saveSendCodeRequest(email);
            }

            return true;

        } catch (Exception e) {
            log.error("Failed write info about send code requests for: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void saveSendCodeRequest(String email) {

        SendCodeRequest sendCodeRequest = SendCodeRequest.builder()
                .email(email)
                .requestsNumber(1)
                .build();

        sendCodeRequestRepository.save(sendCodeRequest);
    }

    @Transactional
    public CheckCodeResponseDto checkCode(CheckCodeRequestDto request) {

        Optional<UserCode> optionalUserCode = userCodeRepository.findByEmail(request.getEmail());

        if (optionalUserCode.isEmpty()) {
            return getFailedCheckCodeResponse(CheckCodeError.CODE_FOR_THIS_EMAIL_NOT_FOUND);
        }

        UserCode userCode = optionalUserCode.get();

        if (DateUtils.isDateExpired(userCode.getCreatedAt(), codeExpirationInMinutes * 60)) {
            userCodeRepository.delete(userCode);
            return getFailedCheckCodeResponse(CheckCodeError.CODE_EXPIRED);
        }

        if (userCode.getAttempts() <= 0) {
            log.warn("User: {} was blocked for {} minutes, because he entered the code incorrectly {} times.",
                    request.getEmail(), lockTime, attemptsNumber);
            return getFailedCheckCodeResponse(CheckCodeError.ATTEMPTS_EXCEEDED);
        }

        if (!userCode.getCode().equals(request.getCode())) {
            userCode.setAttempts(userCode.getAttempts() - 1);
            return getFailedCheckCodeResponse(CheckCodeError.INCORRECT_CODE);
        }

        userCodeRepository.delete(userCode);

        return CheckCodeResponseDto.builder()
                .success(true)
                .build();
    }

    private CheckCodeResponseDto getFailedCheckCodeResponse(CheckCodeError error) {
        return CheckCodeResponseDto.builder()
                .success(false)
                .errorMessage(error)
                .build();
    }
}
