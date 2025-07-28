package by.com.clothify.security.service.restore;

import by.com.clothify.security.model.entity.SendCodeRequest;
import by.com.clothify.security.model.entity.UserCode;
import by.com.clothify.security.repository.SendCodeRequestRepository;
import by.com.clothify.security.repository.UserCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

    private static final int ATTEMPTS_NUMBER = 4;
    private static final int MAX_REQUESTS_NUMBER = 5;

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
            userCode.setAttempts(ATTEMPTS_NUMBER);

        } else {
            userCode = UserCode.builder()
                    .email(email)
                    .code(code)
                    .createdAt(LocalDateTime.now())
                    .attempts(ATTEMPTS_NUMBER)
                    .build();
        }

        userCodeRepository.save(userCode);
    }

    @Transactional
    public boolean writeSendCodeRequestsInfo(String email) {

        try {
            Optional<SendCodeRequest> optionalRequestInfo = sendCodeRequestRepository.findByEmail(email);

            if (optionalRequestInfo.isPresent()) {

                SendCodeRequest requestInfo = optionalRequestInfo.get();

                if (requestInfo.getRequestsNumber() >= MAX_REQUESTS_NUMBER) {
                    log.warn("User: {} exceeded the maximum number of code requests ({})", email, MAX_REQUESTS_NUMBER);
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
}
