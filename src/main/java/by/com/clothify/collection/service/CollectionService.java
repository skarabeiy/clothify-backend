package by.com.clothify.collection.service;

import by.com.clothify.collection.mapper.CollectionMapper;
import by.com.clothify.collection.model.dto.CollectionDto;
import by.com.clothify.collection.model.dto.SavedCollectionDto;
import by.com.clothify.collection.model.entity.Collection;
import by.com.clothify.collection.repository.CollectionRepository;
import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionService {

    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;

    public CollectionDto saveCollection(SavedCollectionDto savedCollectionDto) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            Collection collection = CollectionMapper.mapToCollection(savedCollectionDto, user.getId());
            collection = collectionRepository.save(collection);
            return CollectionMapper.mapToCollectionDto(collection);

        } catch (Exception e) {
            log.error("Failed save collection: {} for user: {}", savedCollectionDto.getName(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
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
