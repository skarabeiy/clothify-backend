package by.com.clothify.images.model.dto;

import by.com.clothify.clothes.model.dto.ClothesBrandDto;
import by.com.clothify.clothes.model.dto.ClothesCategoryDto;
import by.com.clothify.common.model.enums.Season;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Dress")
    private String name;

    @Schema(example = "url to cloud with image")
    private String image;

    @Schema(example = "null")
    @Nullable
    private String backgroundColor;

    private ClothesCategoryDto category;

    private ClothesBrandDto brand;

    @Schema(description = "Image color", example = "#FF0000")
    private String color;

    private Season season;
}
