package by.com.clothify.images.repository;

import by.com.clothify.images.model.entity.ClothingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    void deleteByIdAndUser_Email(Long id, String email);
}
