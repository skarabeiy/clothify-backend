package by.com.clothify.security.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Auth result dto")
public class AuthResponseDto {

    private boolean success;

    @Schema(description = "Token for access",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG1haWwucnUiLCJpYXQiOjE3NTEyMDA2NzcsImV4cCI6MTc1MTIyOTQ3N30.X73mISZpumIzDuPxIxW2gn8c1S_ERzMwUp6XSdWnbqc")
    private String accessToken;

    @Schema(description = "Token for refresh",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG1haWwucnUiLCJpYXQiOjE3NTEyMDA2NzcsImV4cCI6MTc1MjA2NDY3N30.yxPjpnxqC05nEL-h8SgTkO-c1xXCULmd43l79W2-YnM")
    private String refreshToken;

    @Schema(description = "Type of error", example = "PASSWORDS_NOT_MATCH")
    private String errorMessage;
}