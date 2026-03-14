package by.com.clothify.outfit.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchedOutfitClothesDto {

    @Schema(example = "1")
    private Long id;

    @Schema(description = "position of the clothing image center on the screen in relative x", example = "null")
    @Nullable
    private Double positionX;

    @Schema(description = "position of the clothing image center on the screen in relative y", example = "null")
    @Nullable
    private Double positionY;

    @Schema(description = "scale of the clothing image", example = "null")
    @Nullable
    private Double scale;

    @Schema(description = "angle of rotation of the clothing image", example = "null")
    @Nullable
    private Double rotation;

    @JsonProperty("zIndex")
    @Schema(description = "order of the overlay of the clothing image (which element is on top)", example = "null")
    @Nullable
    private Integer zIndex;
}
