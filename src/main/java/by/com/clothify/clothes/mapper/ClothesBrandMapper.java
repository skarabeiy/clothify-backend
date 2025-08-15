package by.com.clothify.clothes.mapper;

import by.com.clothify.clothes.model.dto.ClothesBrandDto;
import by.com.clothify.clothes.model.entity.Clothes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ClothesBrandMapper {

    public static ClothesBrandDto mapToClothesBrandDto(Clothes clothes) {

        return ClothesBrandDto.builder()
                .id(clothes.getClothesBrand().getId())
                .name(clothes.getClothesBrand().getName())
                .build();
    }
}
