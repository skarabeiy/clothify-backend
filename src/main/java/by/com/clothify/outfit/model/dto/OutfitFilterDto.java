package by.com.clothify.outfit.model.dto;

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
public class OutfitFilterDto {

    private Long categoryId;

    private Set<Long> clothingBrandIds;

    private Season season;

    private Set<String> names;
}
