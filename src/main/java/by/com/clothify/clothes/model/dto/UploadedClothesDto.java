package by.com.clothify.clothes.model.dto;

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

    private MultipartFile imageFile;

    @Schema(example = "#FFFFFF")
    @Nullable
    private String backgroundColor;

    @Schema(example = "1")
    private Long categoryId;
}
