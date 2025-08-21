package by.com.clothify.clothes.model.dto;

import by.com.clothify.clothes.model.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothesFilterDto {

    private Long categoryId;

    private Set<String> colors;

    private Set<Long> brandIds;

    private Season season;

    private Set<String> names;
}
