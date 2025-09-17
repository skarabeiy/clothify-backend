package by.com.clothify.collection.service;

import by.com.clothify.collection.mapper.CollectionClothesMapper;
import by.com.clothify.collection.model.dto.CollectionClothesDto;
import by.com.clothify.collection.model.dto.CollectionClothesFilterDto;
import by.com.clothify.collection.model.dto.SavedCollectionClothesDto;
import by.com.clothify.collection.model.entity.Collection;
import by.com.clothify.collection.model.entity.CollectionClothes;
import by.com.clothify.collection.model.enums.CollectionType;
import by.com.clothify.collection.repository.CollectionClothesRepository;
import by.com.clothify.collection.repository.CollectionRepository;
import by.com.clothify.common.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionClothesService {

    private final CollectionRepository collectionRepository;
    private final CollectionClothesRepository collectionClothesRepository;

    public Page<CollectionClothesDto> getFilteredCollectionClothes(CollectionClothesFilterDto filter,
                                                                   int page, int size) {

        final String email = AuthUtils.getEmail();

        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Long> ids = collectionClothesRepository.findFilteredCollectionClothesIds(email, pageable, filter);

            if (ids.isEmpty()) {
                return Page.empty(pageable);
            }

            List<CollectionClothesDto> collectionClothes = collectionClothesRepository
                    .findCollectionClothesByIds(ids.getContent()).stream()
                    .map(CollectionClothesMapper::mapToCollectionClothesDto)
                    .toList();

            return new PageImpl<>(collectionClothes, pageable, ids.getTotalElements());

        } catch (Exception e) {
            log.error("Failed get collection clothes by collection id = {}, for user: {}", filter.getCollectionId(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

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

    @Transactional
    public ResponseEntity<Void> deleteCollectionClothesById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            collectionClothesRepository.deleteByIdAndCollection_User_Email(id, email);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed delete collection clothes with ID = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
