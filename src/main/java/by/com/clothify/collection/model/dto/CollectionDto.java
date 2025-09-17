package by.com.clothify.collection.model.dto;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.collection.model.enums.CollectionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Travelling")
    private String name;

    private CollectionType type;

    @Schema(description = "Clothes in collection")
    private List<ClothesDto> clothes;
}
