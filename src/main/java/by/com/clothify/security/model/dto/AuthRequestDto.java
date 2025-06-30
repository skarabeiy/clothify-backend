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
public class AuthRequestDto {

    @Schema(example = "test@mail.ru")
    private String email;

    @Schema(example = "test")
    private String password;
}
