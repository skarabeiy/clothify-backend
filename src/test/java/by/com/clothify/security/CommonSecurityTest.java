package by.com.clothify.security;

import by.com.clothify.security.model.dto.AuthErrorDto;
import by.com.clothify.security.model.dto.enums.AuthError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class CommonSecurityTest {

    private static final String ENDPOINT_URL = "/profiles";

    @Autowired
    private RestTestClient client;

    @Test
    void unauthorizedTest() {

        AuthErrorDto authErrorDto = client.get()
                .uri(ENDPOINT_URL)
                .exchange()
                .expectStatus().isUnauthorized()
                .returnResult(AuthErrorDto.class)
                .getResponseBody();

        Assertions.assertNotNull(authErrorDto);
        Assertions.assertEquals(AuthError.UNAUTHORIZED, authErrorDto.getErrorType());
    }

    @Test
    void invalidTokenTest() {

        final String randomString = "test";

        AuthErrorDto authErrorDto = client.get()
                .uri(ENDPOINT_URL)
                .header("Authorization", "Bearer " + randomString)
                .exchange()
                .expectStatus().isUnauthorized()
                .returnResult(AuthErrorDto.class)
                .getResponseBody();

        Assertions.assertNotNull(authErrorDto);
        Assertions.assertEquals(AuthError.INVALID_TOKEN, authErrorDto.getErrorType());
    }

    @Test
    void tokenExpiredTest() {

        final String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG1haWwucnUiLCJpYXQiOjE3NTEyMDA2NzcsImV4cCI6MTc1MTIyOTQ3N30.X73mISZpumIzDuPxIxW2gn8c1S_ERzMwUp6XSdWnbqc";

        AuthErrorDto authErrorDto = client.get()
                .uri(ENDPOINT_URL)
                .header("Authorization", "Bearer " + expiredToken)
                .exchange()
                .expectStatus().isUnauthorized()
                .returnResult(AuthErrorDto.class)
                .getResponseBody();

        Assertions.assertNotNull(authErrorDto);
        Assertions.assertEquals(AuthError.ACCESS_TOKEN_EXPIRED, authErrorDto.getErrorType());
    }
}
