package by.com.clothify.plan.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavedOutfitPlanDto {

    @Schema(example = "03.09.2025")
    private String date;

    @Schema(example = "1")
    private Long outfitId;
}
