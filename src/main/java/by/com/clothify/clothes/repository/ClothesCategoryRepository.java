package by.com.clothify.clothes.repository;

import by.com.clothify.clothes.model.entity.ClothesCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesCategoryRepository extends JpaRepository<ClothesCategory, Long> {
}
