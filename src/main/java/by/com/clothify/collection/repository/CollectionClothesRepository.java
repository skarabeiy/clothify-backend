package by.com.clothify.collection.repository;

import by.com.clothify.collection.model.dto.CollectionClothesFilterDto;
import by.com.clothify.collection.model.entity.CollectionClothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionClothesRepository extends JpaRepository<CollectionClothes, Long> {

    Optional<CollectionClothes> findByIdAndCollection_User_Email(Long id, String email);

    @Query("""
            SELECT cc.id FROM CollectionClothes cc
            WHERE cc.collection.id = :#{#filter.collectionId}
            AND cc.collection.user.email = :email
            AND (:#{#filter.packed} IS NULL OR cc.isPacked = :#{#filter.packed})
            AND (COALESCE(:#{#filter.colors}, NULL) IS NULL OR cc.clothes.color IN :#{#filter.colors})
            AND (COALESCE(:#{#filter.brandIds}, NULL) IS NULL OR cc.clothes.brand.id IN :#{#filter.brandIds})
            AND (:#{#filter.season} IS NULL OR cc.clothes.season = :#{#filter.season})
            AND (COALESCE(:#{#filter.names}, NULL) IS NULL OR cc.clothes.name IN :#{#filter.names})
            ORDER BY cc.addedAt DESC
            """)
    Page<Long> findFilteredCollectionClothesIds(String email, Pageable pageable, CollectionClothesFilterDto filter);

    @Query("""
            SELECT cc FROM CollectionClothes cc
            JOIN FETCH cc.clothes c
            JOIN FETCH c.image
            WHERE cc.id IN :ids
            ORDER BY cc.addedAt DESC
            """)
    List<CollectionClothes> findCollectionClothesByIds(List<Long> ids);

    void deleteByIdAndCollection_User_Email(Long id, String email);

    @Query("""
            SELECT cc.clothes.name FROM CollectionClothes cc
            WHERE cc.collection.user.email = :email
            ORDER BY cc.clothes.updatedAt DESC
            """)
    List<String> findCollectionClothesNames(String email);
}
