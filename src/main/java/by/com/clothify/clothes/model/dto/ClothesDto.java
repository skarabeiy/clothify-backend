package by.com.clothify.clothes.model.dto;

import by.com.clothify.common.model.enums.Season;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClothesDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Jacket")
    private String name;

    @Schema(example = "url to cloud with image")
    private String image;

    @Schema(example = "null")
    @Nullable
    private String backgroundColor;

    private ClothesCategoryDto category;

    private ClothesBrandDto brand;

    @Schema(description = "Clothing color", example = "#FF0000")
    private String color;

    private Season season;

    @Schema(example = "120$")
    private String price;

    @Schema(example = "02.09.2024")
    private String dateOfPurchase;
}
