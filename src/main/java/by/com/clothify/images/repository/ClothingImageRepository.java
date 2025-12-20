package by.com.clothify.images.repository;

import by.com.clothify.images.model.dto.ClothingImageFilterDto;
import by.com.clothify.images.model.entity.ClothingImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingImageRepository extends JpaRepository<ClothingImage, Long> {

    @Query("""
            SELECT ci FROM ClothingImage ci
            JOIN ci.user u
            JOIN FETCH ci.image
            JOIN FETCH ci.category
            JOIN FETCH ci.brand
            WHERE ci.id = :id
            AND u.email = :email
            """)
    Optional<ClothingImage> findByIdAndEmail(Long id, String email);

    @Query("""
            SELECT ci.id FROM ClothingImage ci
            WHERE ci.user.email = :email
            AND (:#{#filter.categoryId} IS NULL OR ci.category.id = :#{#filter.categoryId})
            AND (COALESCE(:#{#filter.colors}, NULL) IS NULL OR ci.color IN :#{#filter.colors})
            AND (COALESCE(:#{#filter.brandIds}, NULL) IS NULL OR ci.brand.id IN :#{#filter.brandIds})
            AND (:#{#filter.season} IS NULL OR ci.season = :#{#filter.season})
            AND (COALESCE(:#{#filter.names}, NULL) IS NULL OR ci.name IN :#{#filter.names})
            ORDER BY ci.updatedAt DESC
            """)
    Page<Long> findFilteredClothingImageIds(String email, Pageable pageable, ClothingImageFilterDto filter);

    @Query("""
            SELECT ci FROM ClothingImage ci
            JOIN FETCH ci.image
            WHERE ci.id IN :ids
            ORDER BY ci.updatedAt DESC
            """)
    List<ClothingImage> findClothingImagesByIds(List<Long> ids);

    void deleteByIdAndUser_Email(Long id, String email);

    @Query("""
            SELECT ci.name FROM ClothingImage ci
            WHERE ci.user.email = :email
            ORDER BY ci.updatedAt DESC
            """)
    List<String> findClothingImagesNames(String email);
}
