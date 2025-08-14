package by.com.clothify.clothes.repository;

import by.com.clothify.clothes.model.entity.Clothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    Optional<Clothes> findByIdAndUser_Email(Long id, String email);

    @Query("SELECT c FROM Clothes c " +
            "WHERE c.user.email = :email " +
            "AND (:categoryId IS NULL OR c.category.id = :categoryId) " +
            "ORDER BY c.updatedAt DESC")
    Page<Clothes> findByCategoryIdAndEmail(Long categoryId, String email, Pageable pageable);
}
