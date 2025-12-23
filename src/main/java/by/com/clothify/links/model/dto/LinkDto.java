package by.com.clothify.links.model.dto;

import by.com.clothify.clothes.model.dto.ClothesBrandDto;
import by.com.clothify.clothes.model.dto.ClothesCategoryDto;
import by.com.clothify.common.model.enums.Season;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Name of link")
    private String name;

    @Schema(example = "https://www.adidas.com/us/firebird-track-pants/KD8315.html")
    private String url;

    private ClothesCategoryDto category;

    private ClothesBrandDto brand;

    @Schema(description = "Color", example = "#FF0000")
    private String color;

    private Season season;
}
