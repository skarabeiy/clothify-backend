package by.com.clothify.security.model.dto.apple;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppleAuthRequestDto {

    @Schema(example = "example@mail.com")
    private String email;

    @Schema(description = "Authorization code received in an authorization response sent to Apple app",
            example = "EXAMPLE_AUTHORIZATION_CODE")
    private String appleAuthorizationCode;
}
