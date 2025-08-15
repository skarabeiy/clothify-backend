package by.com.clothify.clothes.model.dto;

import by.com.clothify.clothes.model.enums.Season;
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
public class UploadedClothesDto {

    @Schema(example = "Jacket")
    private String name;

    @Schema(description = "From-data file", requiredMode = Schema.RequiredMode.REQUIRED)
    private MultipartFile imageFile;

    @Schema(example = "#FFFFFF")
    @Nullable
    private String backgroundColor;

    @Schema(example = "1")
    private Long categoryId;

    @Schema(example = "1")
    private Long brandId;

    @Schema(description = "Clothing color", example = "#FF0000")
    private String color;

    private Season season;

    @Schema(example = "120$")
    @Nullable
    private String price;

    @Schema(example = "02.09.2024")
    @Nullable
    private String dateOfPurchase;
}
