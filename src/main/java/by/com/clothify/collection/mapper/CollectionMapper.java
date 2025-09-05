package by.com.clothify.collection.mapper;

import by.com.clothify.collection.model.dto.CollectionDto;
import by.com.clothify.collection.model.dto.SavedCollectionDto;
import by.com.clothify.collection.model.entity.Collection;
import by.com.clothify.security.model.entity.User;

import java.time.LocalDateTime;

public final class CollectionMapper {

    public static CollectionDto mapToCollectionDto(Collection collection) {

        return CollectionDto.builder()
                .id(collection.getId())
                .name(collection.getName())
                .type(collection.getType())
                .build();
    }

    public static Collection mapToCollection(SavedCollectionDto savedCollectionDto, Long userId) {

        return Collection.builder()
                .name(savedCollectionDto.getName())
                .type(savedCollectionDto.getType())
                .user(
                        User.builder()
                                .id(userId)
                                .build()
                )
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
