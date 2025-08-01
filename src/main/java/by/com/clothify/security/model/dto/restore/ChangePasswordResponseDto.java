package by.com.clothify.security.model.dto.restore;

import by.com.clothify.security.model.dto.enums.restore.ChangePasswordError;
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
public class ChangePasswordResponseDto {

    private boolean success;

    @Schema(example = "null")
    private ChangePasswordError errorMessage;
}
