package by.com.clothify.security.model.dto;

import by.com.clothify.security.model.dto.enums.AuthError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dto that will be returned in case of an authentication error")
public class AuthErrorDto {

    @Schema(description = "Type of error", example = "UNAUTHORIZED")
    private AuthError errorType;

    @Schema(example = "Bearer authorization is required for this endpoint")
    private String errorDescription;
}
