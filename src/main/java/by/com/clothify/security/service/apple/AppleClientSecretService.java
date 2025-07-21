package by.com.clothify.security.service.apple;

import by.com.clothify.security.model.entity.AppleClientSecret;
import by.com.clothify.security.repository.AppleClientSecretRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleClientSecretService {

    private static final String APPLE_ID_URL = "https://appleid.apple.com";
    // TODO: перенести в надёжное место
    private static final String APPLE_PRIVATE_KEY = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgJh3tQ8075e9UB5BDulsdnr8uqQEf7cO2p9YfuaKp9B2gCgYIKoZIzj0DAQehRANCAAR2w225RS3s8nYA7BHPhtbwlgLzOupHXCcwEOAoiZdGPzUD9O7ag6Y1rjn/qJej7dATBxm0QdU7kLv/2ThZjABY";

    @Value("${apple.account.client-id}")
    private String clientId;

    @Value("${apple.account.key-id}")
    private String keyId;

    @Value("${apple.account.team-id}")
    private String teamId;

    @Value("${apple.account.client-secret-expiration-time}")
    private Long clientSecretExpirationInMinutes;

    // TODO: Лучше поменять на redis, но пока так
    private final AppleClientSecretRepository appleClientSecretRepository;

    // TODO: пока просто synchronized над всем методом, но можно подумать, где уменшить его скоуп,
    //  для производительности, пока это маловажно
    @Transactional
    public synchronized String getAppleClientSecret() {

        try {
            Optional<AppleClientSecret> optionalSecretCash = appleClientSecretRepository.findFirstBy();

            if (optionalSecretCash.isEmpty()) {
                return createSecretCash();

            } else {
                AppleClientSecret secretCash = optionalSecretCash.get();

                if (isClientSecretExpired(secretCash.getCreatedAt())) {
                    return createSecretCash();
                }

                return secretCash.getClientSecret();
            }
        } catch (Exception e) {
            log.error("Failed get apple client secret");
            throw new RuntimeException(e);
        }
    }

    private String createSecretCash() {

        String clientSecret = generateAppleClientSecret();

        AppleClientSecret appleClientSecret = AppleClientSecret.builder()
                .clientSecret(clientSecret)
                .createdAt(LocalDateTime.now())
                .build();

        appleClientSecretRepository.save(appleClientSecret);
        log.info("Save new apple client secret");

        return clientSecret;
    }

    private String generateAppleClientSecret() {
        try {
            Instant now = Instant.now();

            return Jwts.builder()
                    .header()
                    .add(Map.of(
                            "kid", keyId,
                            "typ", "JWT"
                    ))
                    .and()
                    .issuer(teamId)
                    .audience().add(APPLE_ID_URL).and()
                    .subject(clientId)
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(now.plusSeconds(clientSecretExpirationInMinutes * 60)))
                    .signWith(loadPrivateKey(), Jwts.SIG.ES256)
                    .compact();
        } catch (Exception e) {
            log.error("Failed generate apple client secret");
            throw new RuntimeException(e);
        }
    }

    private static PrivateKey loadPrivateKey() {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(APPLE_PRIVATE_KEY);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);

            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            log.error("Failed load private key for apple");
            throw new RuntimeException(e);
        }
    }

    private boolean isClientSecretExpired(LocalDateTime createdAt) {
        if (createdAt == null) {
            return true;
        }

        return Duration.between(createdAt, LocalDateTime.now()).toSeconds()
                >= (clientSecretExpirationInMinutes * 60);
    }
}
