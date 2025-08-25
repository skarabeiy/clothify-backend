package by.com.clothify.outfit.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutfitCategoryDto implements Serializable {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Casual")
    private String name;
}
