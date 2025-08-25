package by.com.clothify.outfit.mapper;

import by.com.clothify.outfit.model.dto.SavedOutfitDto;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.outfit.model.entity.OutfitCategory;

public class OutfitMapper {

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
