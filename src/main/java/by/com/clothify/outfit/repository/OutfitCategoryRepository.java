package by.com.clothify.outfit.repository;

import by.com.clothify.outfit.model.entity.OutfitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutfitCategoryRepository extends JpaRepository<OutfitCategory, Long> {

    List<OutfitCategory> findAllByOrderByPosition();

    @Query("SELECT cat FROM OutfitCategory cat " +
            "JOIN FETCH Outfit o ON cat.id = o.category.id " +
            "WHERE o.user.email = :email " +
            "ORDER BY cat.position")
    List<OutfitCategory> findUserCategories(String email);
}
