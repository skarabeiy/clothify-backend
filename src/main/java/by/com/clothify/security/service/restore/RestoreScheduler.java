package by.com.clothify.security.service.restore;

import by.com.clothify.security.repository.UserCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestoreScheduler {

    @Value("${restore.code-expiration-time}")
    private Long codeExpirationInMinutes;

    private final UserCodeRepository userCodeRepository;

    @Scheduled(cron = "0 0 4 * * WED")
    public void deleteExpiredUserCodes() {
        try {
            log.info("Start delete expired user codes");

            LocalDateTime expirationTime = LocalDateTime.now().minusSeconds(codeExpirationInMinutes * 60);
            userCodeRepository.deleteByCreatedAtBefore(expirationTime);

            log.info("Successfully finish delete expired user codes");

        } catch (Exception e) {
            log.error("Failed delete expired user codes");
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
