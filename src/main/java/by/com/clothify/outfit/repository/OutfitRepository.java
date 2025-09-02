package by.com.clothify.outfit.repository;

import by.com.clothify.outfit.model.dto.OutfitFilterDto;
import by.com.clothify.outfit.model.entity.Outfit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Long> {

    @Query("""
            SELECT o FROM Outfit o
            JOIN FETCH o.category
            LEFT JOIN FETCH o.outfitClothes oc
            LEFT JOIN FETCH oc.clothes c
            LEFT JOIN FETCH c.image
            WHERE o.id = :id
            AND o.user.email = :email
            ORDER BY oc.addedAt DESC
            """)
    Optional<Outfit> findByIdAndEmail(Long id, String email);

    @Query("""
            SELECT o.id FROM Outfit o
            LEFT JOIN o.outfitClothes oc
            WHERE o.user.email = :email
            AND (:#{#filter.categoryId} IS NULL OR o.category.id = :#{#filter.categoryId})
            AND (COALESCE(:#{#filter.clothingBrandIds}, NULL) IS NULL OR oc.clothes.brand.id IN :#{#filter.clothingBrandIds})
            AND (:#{#filter.season} IS NULL OR o.season = :#{#filter.season})
            AND (COALESCE(:#{#filter.names}, NULL) IS NULL OR o.name IN :#{#filter.names})
            ORDER BY o.updatedAt DESC
            """)
    Page<Long> findFilteredOutfitIds(String email, Pageable pageable, OutfitFilterDto filter);

    @Query("""
            SELECT DISTINCT o FROM Outfit o
            LEFT JOIN FETCH o.outfitClothes oc
            LEFT JOIN FETCH oc.clothes c
            LEFT JOIN FETCH c.image
            WHERE o.id IN :ids
            """)
    List<Outfit> findOutfitsByIds(List<Long> ids);

    void deleteByIdAndUser_Email(Long id, String email);

    @Query("SELECT o.name FROM Outfit o " +
            "WHERE o.user.email = :email " +
            "ORDER BY o.updatedAt DESC")
    List<String> findOutfitNames(String email);

    @Query("""
            SELECT o.id FROM Outfit o
            JOIN o.outfitClothes oc
            JOIN oc.clothes c
            WHERE c.id = :clothingId
            ORDER BY o.updatedAt DESC
            """)
    List<Long> findAllByClothingId(Long clothingId);
}
