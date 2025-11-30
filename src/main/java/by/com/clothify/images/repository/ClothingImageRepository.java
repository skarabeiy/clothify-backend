package by.com.clothify.images.repository;

import by.com.clothify.images.model.entity.ClothingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingImageRepository extends JpaRepository<ClothingImage, Long> {

}
