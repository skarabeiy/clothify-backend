package by.com.clothify.security.service.apple;

import by.com.clothify.security.model.dto.apple.AppleResponseDto;
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

import static by.com.clothify.security.util.AuthConstants.APPLE_ID_URL;

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

        var res = getAppleApiClient().post()
                .uri(APPLE_TOKEN_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(createRequestBody(appleAuthorizationCode))
                .retrieve()
                .body(String.class);

        return null;
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

//        @JsonProperty("refresh_token")

    }
}
