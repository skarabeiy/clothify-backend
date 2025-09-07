package by.com.clothify.collection.model.dto;

import by.com.clothify.collection.model.enums.CollectionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchedCollectionDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "null")
    @Nullable
    private String name;

    @Schema(example = "null")
    @Nullable
    private CollectionType type;
}
