package by.com.clothify.collection.service;

import by.com.clothify.collection.mapper.CollectionClothesMapper;
import by.com.clothify.collection.model.dto.SavedCollectionClothesDto;
import by.com.clothify.collection.model.entity.Collection;
import by.com.clothify.collection.model.entity.CollectionClothes;
import by.com.clothify.collection.model.enums.CollectionType;
import by.com.clothify.collection.repository.CollectionClothesRepository;
import by.com.clothify.collection.repository.CollectionRepository;
import by.com.clothify.common.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionClothesService {

    private final CollectionRepository collectionRepository;
    private final CollectionClothesRepository collectionClothesRepository;

    public ResponseEntity<Void> saveCollectionClothes(SavedCollectionClothesDto savedClothesDto) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Collection> optionalCollection = collectionRepository
                    .findByIdAndUser_Email(savedClothesDto.getCollectionId(), email);

            if (optionalCollection.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Boolean packed = CollectionType.PACKAGING == optionalCollection.get().getType()
                    ? false
                    : null;

            CollectionClothes collectionClothes = CollectionClothesMapper.mapToCollectionClothes(savedClothesDto, packed);
            collectionClothesRepository.save(collectionClothes);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed save clothing with ID = {}, for collection with ID = {}, for user: {}",
                    savedClothesDto.getClothesId(), savedClothesDto.getCollectionId(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
