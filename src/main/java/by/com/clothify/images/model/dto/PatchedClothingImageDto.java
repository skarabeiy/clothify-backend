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
public class PatchedClothingImageDto {

    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Nullable
    private String name;

    @Schema(description = "From-data file")
    @Nullable
    private MultipartFile imageFile;

    @Nullable
    private String backgroundColor;

    @Nullable
    private Long categoryId;

    @Nullable
    private Long brandId;

    @Schema(description = "Image color")
    @Nullable
    private String color;

    @Nullable
    private Season season;
}
