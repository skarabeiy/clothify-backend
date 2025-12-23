package by.com.clothify.links.model.dto;

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
public class PatchedLinkDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Name of link")
    @Nullable
    private String name;

    @Schema(example = "null")
    @Nullable
    private String url;

    @Schema(example = "1")
    @Nullable
    private Long categoryId;

    @Schema(example = "null")
    @Nullable
    private Long brandId;

    @Schema(description = "Item color", example = "null")
    @Nullable
    private String color;

    @Nullable
    @Schema(example = "null")
    private Season season;
}
