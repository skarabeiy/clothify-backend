package by.com.clothify.collection.service;

import by.com.clothify.clothes.mapper.ClothesMapper;
import by.com.clothify.collection.mapper.CollectionMapper;
import by.com.clothify.collection.model.dto.CollectionDto;
import by.com.clothify.collection.model.dto.PatchedCollectionDto;
import by.com.clothify.collection.model.dto.SavedCollectionDto;
import by.com.clothify.collection.model.entity.Collection;
import by.com.clothify.collection.model.entity.CollectionClothes;
import by.com.clothify.collection.model.enums.CollectionType;
import by.com.clothify.collection.repository.CollectionRepository;
import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static by.com.clothify.collection.mapper.CollectionMapper.mapToCollectionDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionService {

    private static final int CLOTHING_PREVIEW_NUMBER = 4;

    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;

    public Page<CollectionDto> getCollectionsByType(CollectionType type, int page, int size) {

        final String email = AuthUtils.getEmail();

        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Long> collectionIds = collectionRepository.findIdsByTypeAndEmail(type, email, pageable);

            if (collectionIds.isEmpty()) {
                return Page.empty(pageable);
            }

            List<Collection> collections = collectionRepository.findByIds(collectionIds.getContent());

            return new PageImpl<>(mapToCollectionDtoList(collections), pageable, collectionIds.getTotalElements());

        } catch (Exception e) {
            log.error("Failed get collections by type: {}, for user: {}", type, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private List<CollectionDto> mapToCollectionDtoList(List<Collection> collections) {

        return collections.stream()
                .map(collection -> {
                    CollectionDto collectionDto = CollectionMapper.mapToCollectionDto(collection);
                    collectionDto.setClothes(
                            collection.getCollectionClothes().stream()
                                    .limit(CLOTHING_PREVIEW_NUMBER)
                                    .sorted(Comparator.comparing(CollectionClothes::getAddedAt).reversed())
                                    .map(colClothes -> ClothesMapper.mapToPageClothesDto(colClothes.getClothes()))
                                    .toList()
                    );
                    return collectionDto;
                })
                .toList();
    }

    public CollectionDto saveCollection(SavedCollectionDto savedCollectionDto) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            Collection collection = CollectionMapper.mapToCollection(savedCollectionDto, user.getId());
            collection = collectionRepository.save(collection);
            return mapToCollectionDto(collection);

        } catch (Exception e) {
            log.error("Failed save collection: {} for user: {}", savedCollectionDto.getName(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<CollectionDto> patchCollection(PatchedCollectionDto patchedCollectionDto) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Collection> optionalCollection = collectionRepository
                    .findByIdAndUser_Email(patchedCollectionDto.getId(), email);

            if (optionalCollection.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Collection collection = optionalCollection.get();

            setPatchedCollectionFields(collection, patchedCollectionDto);

            collection.setUpdatedAt(LocalDateTime.now());

            collection = collectionRepository.save(collection);

            return ResponseEntity.ok(mapToCollectionDto(collection));

        } catch (Exception e) {
            log.error("Failed patch collection with ID = {} for user: {}", patchedCollectionDto.getId(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static void setPatchedCollectionFields(Collection collection, PatchedCollectionDto patchedCollectionDto) {

        if (patchedCollectionDto.getName() != null) {
            collection.setName(patchedCollectionDto.getName());
        }

        if (patchedCollectionDto.getType() != null) {
            collection.setType(patchedCollectionDto.getType());
        }
    }

    @Transactional
    public ResponseEntity<Void> deleteCollectionById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            collectionRepository.deleteByIdAndUser_Email(id, email);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed delete collection with ID = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
