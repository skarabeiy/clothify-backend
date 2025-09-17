package by.com.clothify.collection.mapper;

import by.com.clothify.clothes.model.entity.Clothes;
import by.com.clothify.collection.model.dto.SavedCollectionClothesDto;
import by.com.clothify.collection.model.entity.Collection;
import by.com.clothify.collection.model.entity.CollectionClothes;

import java.time.LocalDateTime;

public final class CollectionClothesMapper {

    public static CollectionClothes mapToCollectionClothes(SavedCollectionClothesDto savedClothesDto, Boolean packed) {

        return CollectionClothes.builder()
                .collection(
                        Collection.builder()
                                .id(savedClothesDto.getCollectionId())
                                .build()
                )
                .clothes(
                        Clothes.builder()
                                .id(savedClothesDto.getClothesId())
                                .build()
                )
                .isPacked(packed)
                .addedAt(LocalDateTime.now())
                .build();
    }
}
