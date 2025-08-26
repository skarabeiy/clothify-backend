package by.com.clothify.outfit.repository;

import by.com.clothify.outfit.model.dto.OutfitFilterDto;
import by.com.clothify.outfit.model.entity.Outfit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Long> {

    Optional<Outfit> findByIdAndUser_Email(Long id, String email);

    @Query("SELECT o FROM Outfit o " +
            "WHERE o.user.email = :email " +
            "AND (:#{#filter.categoryId} IS NULL OR o.category.id = :#{#filter.categoryId}) " +
            "ORDER BY o.updatedAt DESC")
    Page<Outfit> findFilteredOutfits(String email, Pageable pageable, OutfitFilterDto filter);
}
