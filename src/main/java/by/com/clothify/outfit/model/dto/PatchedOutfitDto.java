package by.com.clothify.outfit.model.dto;

import by.com.clothify.common.model.enums.Season;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchedOutfitDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "null")
    @Nullable
    private String name;

    @Schema(example = "null")
    @Nullable
    private String backgroundColor;

    @Schema(example = "null")
    @Nullable
    private Long categoryId;

    @Schema(example = "null")
    @Nullable
    private Season season;

    private List<SavedOutfitClothesDto> addedClothes;

    private List<SavedOutfitClothesDto> updatedClothes;

    private List<SavedOutfitClothesDto> deletedClothes;
}
