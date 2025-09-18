package by.com.clothify.collection.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchedCollectionClothesDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "true")
    private Boolean packed;
}
