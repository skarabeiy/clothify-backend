package by.com.clothify.clothes.repository;

import by.com.clothify.clothes.model.entity.ClothesCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesCategoryRepository extends JpaRepository<ClothesCategory, Long> {

    @Query("SELECT cat FROM ClothesCategory cat " +
            "JOIN FETCH Clothes c ON cat.id = c.category.id " +
            "JOIN FETCH User u ON c.user.id = u.id " +
            "WHERE u.email = :email")
    List<ClothesCategory> findUserCategories(String email);
}
