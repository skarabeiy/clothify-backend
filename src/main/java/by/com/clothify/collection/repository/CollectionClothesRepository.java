package by.com.clothify.collection.repository;

import by.com.clothify.collection.model.dto.CollectionClothesFilterDto;
import by.com.clothify.collection.model.entity.CollectionClothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionClothesRepository extends JpaRepository<CollectionClothes, Long> {

    @Query("""
            SELECT cc.id FROM CollectionClothes cc
            WHERE cc.collection.id = :#{#filter.collectionId}
            AND cc.collection.user.email = :email
            ORDER BY cc.addedAt DESC
            """)
    Page<Long> findFilteredCollectionClothesIds(String email, Pageable pageable, CollectionClothesFilterDto filter);

    @Query("""
            SELECT cc FROM CollectionClothes cc
            JOIN FETCH cc.clothes c
            JOIN FETCH c.image
            WHERE cc.id IN :ids
            """)
    List<CollectionClothes> findCollectionClothesByIds(List<Long> ids);
}
