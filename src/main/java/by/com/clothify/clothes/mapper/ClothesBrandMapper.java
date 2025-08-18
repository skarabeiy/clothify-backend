package by.com.clothify.clothes.mapper;

import by.com.clothify.clothes.model.dto.ClothesBrandDto;
import by.com.clothify.clothes.model.entity.ClothesBrand;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ClothesBrandMapper {

    public static ClothesBrandDto mapToClothesBrandDto(ClothesBrand brand) {

        return ClothesBrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
