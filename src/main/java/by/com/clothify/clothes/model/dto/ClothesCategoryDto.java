package by.com.clothify.clothes.model.dto;

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
public class ClothesCategoryDto implements Serializable {

    @Schema(example = "null")
    private Long id;

    @Schema(example = "All")
    private String name;
}
