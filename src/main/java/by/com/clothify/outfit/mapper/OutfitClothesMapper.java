package by.com.clothify.outfit.mapper;

import by.com.clothify.clothes.model.entity.Clothes;
import by.com.clothify.outfit.model.dto.OutfitClothesDto;
import by.com.clothify.outfit.model.dto.SavedOutfitClothesDto;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.outfit.model.entity.OutfitClothes;

import java.time.LocalDateTime;

public class OutfitClothesMapper {

    public static OutfitClothesDto mapToOutfitClothesDto(OutfitClothes outfitClothes) {

        return OutfitClothesDto.builder()
                .id(outfitClothes.getClothes().getId())
                .name(outfitClothes.getClothes().getName())
                .image(outfitClothes.getClothes().getImage().getUrl())
                .positionX(outfitClothes.getPositionX())
                .positionY(outfitClothes.getPositionY())
                .scale(outfitClothes.getScale())
                .rotation(outfitClothes.getRotation())
                .zIndex(outfitClothes.getZIndex())
                .build();
    }

    public static OutfitClothes mapToOutfitClothes(SavedOutfitClothesDto savedOutfitClothesDto, Long outfitId) {

        return OutfitClothes.builder()
                .outfit(
                        Outfit.builder()
                                .id(outfitId)
                                .build()
                )
                .clothes(
                        Clothes.builder()
                                .id(savedOutfitClothesDto.getId())
                                .build()
                )
                .positionX(savedOutfitClothesDto.getPositionX())
                .positionY(savedOutfitClothesDto.getPositionY())
                .scale(savedOutfitClothesDto.getScale())
                .rotation(savedOutfitClothesDto.getRotation())
                .zIndex(savedOutfitClothesDto.getZIndex())
                .addedAt(LocalDateTime.now())
                .build();
    }
}
