package by.com.clothify.clothes.mapper;

import by.com.clothify.clothes.model.dto.ClothesBrandDto;
import by.com.clothify.clothes.model.entity.Clothes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ClothesBrandMapper {

    public static ClothesBrandDto mapToClothesBrandDto(Clothes clothes) {

        try {
            if (clothes.getCommonBrand() != null) {

                return ClothesBrandDto.builder()
                        .id(clothes.getCommonBrand().getId())
                        .name(clothes.getCommonBrand().getName())
                        .build();
            } else {

                return ClothesBrandDto.builder()
                        .id(clothes.getCustomBrand().getId())
                        .name(clothes.getCustomBrand().getName())
                        .build();
            }

        } catch (Exception e) {
            log.error("Failed map to ClothesBrandDto");
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
