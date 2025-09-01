package by.com.clothify.outfit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutfitClothesDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private Long clothingId;

    @Schema(example = "Jacket")
    private String name;

    @Schema(example = "url to cloud with clothing image")
    private String image;

    @Schema(description = "position of the clothing image center on the screen in relative x", example = "0.45")
    private Double positionX;

    @Schema(description = "position of the clothing image center on the screen in relative y", example = "0.72")
    private Double positionY;

    @Schema(description = "scale of the clothing image", example = "1.0")
    private Double scale;

    @Schema(description = "angle of rotation of the clothing image", example = "0.0")
    private Double rotation;

    @Schema(description = "order of the overlay of the clothing image (which element is on top)", example = "2")
    private Integer zIndex;
}
