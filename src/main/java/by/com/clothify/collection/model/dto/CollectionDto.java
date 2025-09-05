package by.com.clothify.collection.model.dto;

import by.com.clothify.collection.model.enums.CollectionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Travelling")
    private String name;

    private CollectionType type;
}
