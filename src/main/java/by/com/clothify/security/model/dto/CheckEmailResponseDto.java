package by.com.clothify.security.model.dto;

import by.com.clothify.security.model.dto.enums.CheckEmailResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckEmailResponseDto {

    @Schema(description = "Type of check result")
    private CheckEmailResult resultMessage;
}
