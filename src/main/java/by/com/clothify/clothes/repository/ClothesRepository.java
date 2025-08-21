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

    Optional<Clothes> findByIdAndUser_Email(Long id, String email);

    @Query("SELECT c FROM Clothes c " +
            "WHERE c.user.email = :email " +
            "AND (:#{#filter.categoryId} IS NULL OR c.category.id = :#{#filter.categoryId}) " +
            "AND (COALESCE(:#{#filter.colors}, NULL) IS NULL OR c.color IN :#{#filter.colors}) " +
            "AND (COALESCE(:#{#filter.brandIds}, NULL) IS NULL OR c.brand.id IN :#{#filter.brandIds}) " +
            "AND (:#{#filter.season} IS NULL OR c.season = :#{#filter.season}) " +
            "AND (COALESCE(:#{#filter.names}, NULL) IS NULL OR c.name IN :#{#filter.names}) " +
            "ORDER BY c.updatedAt DESC")
    Page<Clothes> findFilteredClothes(String email, Pageable pageable, ClothesFilterDto filter);

    void deleteByIdAndUser_Email(Long id, String email);

    @Query("SELECT c.name FROM Clothes c " +
            "WHERE c.user.email = :email " +
            "ORDER BY c.updatedAt DESC")
    List<String> findClothesNames(String email);
}
