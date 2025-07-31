package by.com.clothify.security.model.dto.restore;

import by.com.clothify.security.model.dto.enums.restore.CheckCodeError;
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
public class CheckCodeResponseDto {

    private boolean success;

    @Schema(description = "Type of error", example = "null")
    private CheckCodeError errorMessage;
}
