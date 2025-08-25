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
public class SavedOutfitDto {

    @Schema(example = "Casual Monday outfit")
    private String name;

    @Schema(example = "null")
    @Nullable
    private String backgroundColor;

    private Long categoryId;

    private Season season;

    private List<SavedOutfitClothesDto> clothes;
}
