package by.com.clothify.images.model.dto;

import by.com.clothify.common.model.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothingImageFilterDto {

    private Long categoryId;

    private Set<String> colors;

    private Set<Long> brandIds;

    private Season season;

    private Set<String> names;
}
