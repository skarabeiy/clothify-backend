package by.com.clothify.security.model.dto.restore;

import by.com.clothify.security.model.dto.enums.restore.SendCodeError;
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
public class SendCodeResponseDto {

    private boolean success;

    private InfoDto info;

    @Schema(description = "Type of error", example = "null")
    private SendCodeError errorMessage;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InfoDto {

        @Schema(example = "15")
        private Long codeExpirationInMinutes;

        @Schema(description = "The time after which user can send the code again", example = "1")
        private Long sendCodeRetryTimeInMinutes;

        @Schema(description = "Number of attempts to enter the code", example = "4")
        private Integer enterAttemptsNumber;

        @Schema(description = "Maximum number of send-code requests", example = "5")
        private Integer sendCodeRequestMaxNumber;

        @Schema(description = "The blocking time when the maximum number of send-code requests is exceeded", example = "15")
        private Long requestLockTimeInMinutes;
    }
}
