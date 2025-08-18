package by.com.clothify.clothes.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothesBrandRequestDto {

    @Schema(description = "Custom brand name", example = "Mua")
    private String name;
}
