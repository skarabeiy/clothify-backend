package by.com.clothify.collection.model.dto;

import by.com.clothify.clothes.model.dto.ClothesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionClothesDto {

    @Schema(example = "1")
    private Long id;

    private ClothesDto clothes;

    @Schema(example = "null")
    private Boolean packed;
}
