package by.com.clothify.clothes.mapper;

import by.com.clothify.clothes.model.dto.ClothesCategoryDto;
import by.com.clothify.clothes.model.entity.ClothesCategory;

public final class ClothesCategoryMapper {

    public static ClothesCategoryDto mapToCategoryDto(ClothesCategory category) {
        return ClothesCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
