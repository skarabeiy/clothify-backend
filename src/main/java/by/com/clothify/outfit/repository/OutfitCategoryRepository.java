package by.com.clothify.outfit.repository;

import by.com.clothify.outfit.model.entity.OutfitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutfitCategoryRepository extends JpaRepository<OutfitCategory, Long> {

    List<OutfitCategory> findAllByOrderByPosition();
}
