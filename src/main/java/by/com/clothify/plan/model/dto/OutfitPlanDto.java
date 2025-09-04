package by.com.clothify.plan.model.dto;

import by.com.clothify.outfit.model.dto.OutfitDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutfitPlanDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "03.09.2025")
    private String date;

    private OutfitDto outfit;
}
