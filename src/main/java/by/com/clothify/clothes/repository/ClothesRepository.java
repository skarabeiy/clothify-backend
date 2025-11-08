package by.com.clothify.clothes.repository;

import by.com.clothify.clothes.model.dto.ClothesFilterDto;
import by.com.clothify.clothes.model.entity.Clothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    @Query("""
            SELECT c FROM Clothes c
            JOIN c.user u
            JOIN FETCH c.image
            JOIN FETCH c.category
            JOIN FETCH c.brand
            WHERE c.id = :id
            AND u.email = :email
            """)
    Optional<Clothes> findByIdAndEmail(Long id, String email);

    @Query("""
            SELECT c.id FROM Clothes c
            WHERE c.user.email = :email
            AND (:#{#filter.categoryId} IS NULL OR c.category.id = :#{#filter.categoryId})
            AND (COALESCE(:#{#filter.colors}, NULL) IS NULL OR c.color IN :#{#filter.colors})
            AND (COALESCE(:#{#filter.brandIds}, NULL) IS NULL OR c.brand.id IN :#{#filter.brandIds})
            AND (:#{#filter.season} IS NULL OR c.season = :#{#filter.season})
            AND (COALESCE(:#{#filter.names}, NULL) IS NULL OR c.name IN :#{#filter.names})
            ORDER BY c.updatedAt DESC
            """)
    Page<Long> findFilteredClothesIds(String email, Pageable pageable, ClothesFilterDto filter);

    @Query("""
            SELECT c FROM Clothes c
            JOIN FETCH c.image
            WHERE c.id IN :ids
            ORDER BY c.updatedAt DESC
            """)
    List<Clothes> findClothesByIds(List<Long> ids);

    void deleteByIdAndUser_Email(Long id, String email);

    @Query("""
            SELECT c.name FROM Clothes c
            WHERE c.user.email = :email
            ORDER BY c.updatedAt DESC
            """)
    List<String> findClothesNames(String email);

    @Query("""
            SELECT c FROM Clothes c
            JOIN FETCH c.category
            WHERE c.user.email = :email
            ORDER BY c.category.position
            """)
    List<Clothes> findAllByUser_Email(String email);

    @Query("""
            SELECT c FROM Clothes c
            JOIN FETCH c.image
            WHERE c.user.email = :email
            ORDER BY c.updatedAt
            """)
    List<Clothes> findAllByEmailForAdvancedStatistic(String email);
}
