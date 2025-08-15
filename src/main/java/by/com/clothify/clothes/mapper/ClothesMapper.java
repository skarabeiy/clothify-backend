package by.com.clothify.clothes.mapper;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.model.entity.Clothes;
import by.com.clothify.common.util.DateUtils;

public final class ClothesMapper {

    public static ClothesDto mapToClothesDto(Clothes clothes) {

        return ClothesDto.builder()
                .id(clothes.getId())
                .name(clothes.getName())
                .image(clothes.getImage().getUrl())
                .backgroundColor(clothes.getBackgroundColor())
                .category(ClothesCategoryMapper.mapToCategoryDto(clothes.getCategory()))
                .brand(ClothesBrandMapper.mapToClothesBrandDto(clothes))
                .color(clothes.getColor())
                .season(clothes.getSeason())
                .price(clothes.getPrice())
                .dateOfPurchase(DateUtils.convertToString(clothes.getDateOfPurchase()))
                .build();
    }
}
