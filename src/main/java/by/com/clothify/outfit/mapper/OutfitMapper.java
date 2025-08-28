package by.com.clothify.outfit.mapper;

import by.com.clothify.outfit.model.dto.OutfitDto;
import by.com.clothify.outfit.model.dto.SavedOutfitDto;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.outfit.model.entity.OutfitCategory;
import by.com.clothify.outfit.model.entity.OutfitClothes;

import java.util.List;

public class OutfitMapper {

    public static OutfitDto mapToPageOutfitDto(Outfit outfit) {

        return OutfitDto.builder()
                .id(outfit.getId())
                .name(outfit.getName())
                .backgroundColor(outfit.getBackgroundColor())
                .clothes(
                        outfit.getOutfitClothes().stream()
                                .map(OutfitClothesMapper::mapToOutfitClothesDto)
                                .toList()
                )
                .build();
    }

    public static OutfitDto mapToOutfitDto(Outfit outfit) {

        return mapToOutfitDto(outfit, outfit.getOutfitClothes());
    }

    public static OutfitDto mapToOutfitDto(Outfit outfit, List<OutfitClothes> outfitClothes) {

        return OutfitDto.builder()
                .id(outfit.getId())
                .name(outfit.getName())
                .backgroundColor(outfit.getBackgroundColor())
                .category(OutfitCategoryMapper.mapToCategoryDto(outfit.getCategory()))
                .season(outfit.getSeason())
                .clothes(
                        outfitClothes.stream()
                                .map(OutfitClothesMapper::mapToOutfitClothesDto)
                                .toList()
                )
                .build();
    }

    public static Outfit mapToOutfit(SavedOutfitDto savedOutfitDto) {

        return Outfit.builder()
                .name(savedOutfitDto.getName())
                .backgroundColor(savedOutfitDto.getBackgroundColor())
                .category(
                        OutfitCategory.builder()
                                .id(savedOutfitDto.getCategoryId())
                                .build()
                )
                .season(savedOutfitDto.getSeason())
                .build();
    }
}
