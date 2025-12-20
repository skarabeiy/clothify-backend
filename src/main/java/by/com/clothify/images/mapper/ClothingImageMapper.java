package by.com.clothify.images.mapper;

import by.com.clothify.clothes.mapper.ClothesBrandMapper;
import by.com.clothify.clothes.mapper.ClothesCategoryMapper;
import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.model.entity.Clothes;
import by.com.clothify.clothes.model.entity.ClothesBrand;
import by.com.clothify.clothes.model.entity.ClothesCategory;
import by.com.clothify.images.model.dto.ClothingImageDto;
import by.com.clothify.images.model.dto.UploadedClothingImageDto;
import by.com.clothify.images.model.entity.ClothingImage;

public final class ClothingImageMapper {

    public static ClothingImageDto mapToPageClothingImageDto(ClothingImage clothingImage) {

        return ClothingImageDto.builder()
                .id(clothingImage.getId())
                .name(clothingImage.getName())
                .image(clothingImage.getImage().getUrl())
                .backgroundColor(clothingImage.getBackgroundColor())
                .build();
    }

    public static ClothingImageDto mapToClothingImageDto(ClothingImage clothingImage) {

        return ClothingImageDto.builder()
                .id(clothingImage.getId())
                .name(clothingImage.getName())
                .image(clothingImage.getImage().getUrl())
                .backgroundColor(clothingImage.getBackgroundColor())
                .category(ClothesCategoryMapper.mapToCategoryDto(clothingImage.getCategory()))
                .brand(ClothesBrandMapper.mapToClothesBrandDto(clothingImage.getBrand()))
                .color(clothingImage.getColor())
                .season(clothingImage.getSeason())
                .build();
    }

    public static ClothingImage mapToClothingImage(UploadedClothingImageDto uploadedImageDto) {

        return ClothingImage.builder()
                .name(uploadedImageDto.getName())
                .backgroundColor(uploadedImageDto.getBackgroundColor())
                .category(
                        ClothesCategory.builder()
                                .id(uploadedImageDto.getCategoryId())
                                .build()
                )
                .brand(
                        ClothesBrand.builder()
                                .id(uploadedImageDto.getBrandId())
                                .build()
                )
                .color(uploadedImageDto.getColor())
                .season(uploadedImageDto.getSeason())
                .build();
    }
}
