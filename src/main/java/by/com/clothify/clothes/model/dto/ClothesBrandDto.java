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
public class ClothesBrandDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Gucci")
    private String name;
}
