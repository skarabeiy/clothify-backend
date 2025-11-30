package by.com.clothify.images.model.dto;

import by.com.clothify.common.model.enums.Season;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadedClothingImageDto {

    @Schema(example = "Jacket", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "From-data file", requiredMode = Schema.RequiredMode.REQUIRED)
    private MultipartFile imageFile;

    @Schema(example = "#FFFFFF")
    @Nullable
    private String backgroundColor;

    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;

    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long brandId;

    @Schema(description = "Image color", example = "#FF0000", requiredMode = Schema.RequiredMode.REQUIRED)
    private String color;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Season season;
}
