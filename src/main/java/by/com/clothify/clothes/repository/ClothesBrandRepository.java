package by.com.clothify.clothes.repository;

import by.com.clothify.clothes.model.entity.ClothesBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesBrandRepository extends JpaRepository<ClothesBrand, Long> {
}
