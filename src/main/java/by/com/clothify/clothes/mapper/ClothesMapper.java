package by.com.clothify.clothes.mapper;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.model.dto.UploadedClothesDto;
import by.com.clothify.clothes.model.entity.Clothes;
import by.com.clothify.clothes.model.entity.ClothesBrand;
import by.com.clothify.clothes.model.entity.ClothesCategory;
import by.com.clothify.common.util.DateUtils;
import by.com.clothify.outfit.mapper.OutfitMapper;
import by.com.clothify.outfit.model.entity.Outfit;

import java.util.List;

public final class ClothesMapper {

    public static ClothesDto mapToPageClothesDto(Clothes clothes) {

        return ClothesDto.builder()
                .id(clothes.getId())
                .name(clothes.getName())
                .image(clothes.getImage().getUrl())
                .backgroundColor(clothes.getBackgroundColor())
                .build();
    }

    public static ClothesDto mapToClothesWithOutfitsDto(Clothes clothes, List<Outfit> outfits) {

        ClothesDto clothesDto = mapToClothesDto(clothes);

        clothesDto.setOutfits(OutfitMapper.mapToListOfPageOutfitDto(outfits));

        return clothesDto;
    }

    public static ClothesDto mapToClothesDto(Clothes clothes) {

        return ClothesDto.builder()
                .id(clothes.getId())
                .name(clothes.getName())
                .image(clothes.getImage().getUrl())
                .backgroundColor(clothes.getBackgroundColor())
                .category(ClothesCategoryMapper.mapToCategoryDto(clothes.getCategory()))
                .brand(ClothesBrandMapper.mapToClothesBrandDto(clothes.getBrand()))
                .color(clothes.getColor())
                .season(clothes.getSeason())
                .price(clothes.getPrice())
                .dateOfPurchase(DateUtils.convertToString(clothes.getDateOfPurchase()))
                .build();
    }

    public static ClothesDto mapToBasicStatisticClothesDto(Clothes clothes) {

        return ClothesDto.builder()
                .id(clothes.getId())
                .name(clothes.getName())
                .category(ClothesCategoryMapper.mapToCategoryDto(clothes.getCategory()))
                .price(clothes.getPrice())
                .build();
    }

    public static ClothesDto mapToAdnvancedStatisticClothesDto(Clothes clothes) {

        return ClothesDto.builder()
                .id(clothes.getId())
                .name(clothes.getName())
                .image(clothes.getImage().getUrl())
                .backgroundColor(clothes.getBackgroundColor())
                .build();
    }

    public static Clothes mapToClothes(UploadedClothesDto uploadedClothes) {

        return Clothes.builder()
                .name(uploadedClothes.getName())
                .backgroundColor(uploadedClothes.getBackgroundColor())
                .category(
                        ClothesCategory.builder()
                                .id(uploadedClothes.getCategoryId())
                                .build()
                )
                .brand(
                        ClothesBrand.builder()
                                .id(uploadedClothes.getBrandId())
                                .build()
                )
                .color(uploadedClothes.getColor())
                .season(uploadedClothes.getSeason())
                .price(uploadedClothes.getPrice())
                .dateOfPurchase(DateUtils.covertToLocaleDate(uploadedClothes.getDateOfPurchase()))
                .build();
    }
}
