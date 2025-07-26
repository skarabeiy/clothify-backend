package by.com.clothify.security.model.dto.restore;

import by.com.clothify.security.model.dto.enums.SendCodeError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendCodeResponseDto {

    private boolean success;

    @Schema(description = "Type of error")
    private SendCodeError errorMessage;
}
