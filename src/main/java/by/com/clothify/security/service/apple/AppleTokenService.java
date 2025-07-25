package by.com.clothify.security.service.apple;

import by.com.clothify.security.model.dto.apple.AppleResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static by.com.clothify.security.util.AuthConstants.APPLE_ID_URL;
import static by.com.clothify.security.util.AuthConstants.OBJECT_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleTokenService {

    private static final String APPLE_TOKEN_URI = "/auth/oauth2/v2/token";
    private static final String AUTH_GRANT_TYPE = "authorization_code";

    @Value("${apple.account.client-id}")
    private String clientId;

    private final AppleClientSecretService appleClientSecretService;

    public AppleResponseDto makeTokenRequestToApple(String appleAuthorizationCode) {

        try {
            AppleTokenResponseDto tokenResponse = getAppleApiClient().post()
                    .uri(APPLE_TOKEN_URI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(createRequestBody(appleAuthorizationCode))
                    .retrieve()
                    .body(AppleTokenResponseDto.class);

            return makeFinalResponse(tokenResponse);

        } catch (Exception e) {
            log.error("Failed {} request", APPLE_TOKEN_URI);
            log.error(e.getMessage(), e);

            return AppleResponseDto.builder()
                    .success(false)
                    .build();
        }
    }

    private static RestClient getAppleApiClient() {

        return RestClient.builder()
                .baseUrl(APPLE_ID_URL)
                .build();
    }

    private MultiValueMap<String, String> createRequestBody(String appleAuthorizationCode) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("client_id", clientId);
        body.add("client_secret", appleClientSecretService.getAppleClientSecret());
        body.add("code", appleAuthorizationCode);
        body.add("grant_type", AUTH_GRANT_TYPE);

        return body;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AppleTokenResponseDto {

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("expires_in")
        private String expiresIn;

        @JsonProperty("id_token")
        private String idToken;
    }

    private AppleResponseDto makeFinalResponse(AppleTokenResponseDto tokenResponse) {

        try {
            AppleIdTokenPayloadDto payloadDto = getPayloadFromAppleIdToken(tokenResponse.idToken);

            if (!checkPayload(payloadDto)) {
                log.error("Failed check apple token response");
                return AppleResponseDto.builder()
                        .success(false)
                        .build();
            }

            return AppleResponseDto.builder()
                    .success(true)
                    .email(payloadDto.getEmail())
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Failed make apple response");
            return AppleResponseDto.builder()
                    .success(false)
                    .build();
        }
    }

    private boolean checkPayload(AppleIdTokenPayloadDto payloadDto) {

        if (!APPLE_ID_URL.equals(payloadDto.getIssuer())) {
            return false;
        }

        return clientId.equals(payloadDto.getClientId());
    }

    private static AppleIdTokenPayloadDto getPayloadFromAppleIdToken(String appleIdToken) {

        try {
            String[] parts = appleIdToken.split("\\.");
            if (parts.length != 3) {
                log.error("Invalid apple id token");
                throw new RuntimeException();
            }

            String payload = decodeBase64Url(parts[1]);

            return OBJECT_MAPPER.readValue(payload, AppleIdTokenPayloadDto.class);

        } catch (Exception e) {
            log.error("Failed parse apple id token");
            throw new RuntimeException(e);
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AppleIdTokenPayloadDto {

        @JsonProperty("iss")
        private String issuer;

        @JsonProperty("aud")
        private String clientId;

        @JsonProperty("exp")
        private long expirationTime;

        @JsonProperty("iat")
        private long issuedAt;

        @JsonProperty("sub")
        private String subject;

        @JsonProperty("at_hash")
        private String accessTokenHash;

        private String email;

        @JsonProperty("email_verified")
        private boolean emailVerified;

        @JsonProperty("auth_time")
        private long authTime;

        @JsonProperty("nonce_supported")
        private boolean nonceSupported;
    }

    private static String decodeBase64Url(String base64Url) {

        String base64 = base64Url.replace('-', '+').replace('_', '/');

        switch (base64.length() % 4) {
            case 2:
                base64 += "==";
                break;
            case 3:
                base64 += "=";
                break;
        }

        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
