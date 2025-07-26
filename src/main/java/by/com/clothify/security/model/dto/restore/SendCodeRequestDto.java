package by.com.clothify.security.model.dto.restore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendCodeRequestDto {

    @Schema(example = "skarabeiy@mail.ru")
    private String email;
}
