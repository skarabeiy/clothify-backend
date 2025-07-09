package by.com.clothify.security.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequestDto {

    @Schema(description = "Token for refresh",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG1haWwucnUiLCJpYXQiOjE3NTEyMDA2NzcsImV4cCI6MTc1MjA2NDY3N30.yxPjpnxqC05nEL-h8SgTkO-c1xXCULmd43l79W2-YnM")
    private String refreshToken;
}
