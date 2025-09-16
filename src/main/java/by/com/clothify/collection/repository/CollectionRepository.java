package by.com.clothify.collection.repository;

import by.com.clothify.collection.model.entity.Collection;
import by.com.clothify.collection.model.enums.CollectionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Optional<Collection> findByIdAndUser_Email(Long id, String email);

    @Query("""
            SELECT c.id FROM Collection c
            WHERE c.user.email = :email
            AND (:type IS NULL OR c.type = :type)
            ORDER BY c.updatedAt DESC
            """)
    Page<Long> findIdsByTypeAndEmail(CollectionType type, String email, Pageable pageable);

    @Query("""
            SELECT DISTINCT col FROM Collection col
            LEFT JOIN FETCH col.collectionClothes colc
            LEFT JOIN FETCH colc.clothes c
            LEFT JOIN FETCH c.image
            WHERE col.id IN :ids
            """)
    List<Collection> findByIds(List<Long> ids);

    void deleteByIdAndUser_Email(Long id, String email);
}
