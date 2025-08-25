package by.com.clothify.outfit.repository;

import by.com.clothify.outfit.model.entity.OutfitClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutfitClothesRepository extends JpaRepository<OutfitClothes, Long> {
}
