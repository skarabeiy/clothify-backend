package by.com.clothify.outfit.mapper;

import by.com.clothify.outfit.model.dto.OutfitCategoryDto;
import by.com.clothify.outfit.model.entity.OutfitCategory;

public class OutfitCategoryMapper {

    public static OutfitCategoryDto mapToCategoryDto(OutfitCategory category) {

        return OutfitCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
