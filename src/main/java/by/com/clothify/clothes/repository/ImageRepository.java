package by.com.clothify.clothes.repository;

import by.com.clothify.clothes.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i " +
            "JOIN FETCH Clothes c ON c.image.id = i.id " +
            "WHERE c.id = :clothesId AND c.user.email = :email")
    Optional<Image> findByClothesIdAndEmail(Long clothesId, String email);

    @Query("SELECT i FROM Image i " +
            "JOIN FETCH ClothingImage ci ON ci.image.id = i.id " +
            "WHERE ci.id = :clothingImageId AND ci.user.email = :email")
    Optional<Image> findImageByClothingImageIdAndEmail(Long clothingImageId, String email);
}
