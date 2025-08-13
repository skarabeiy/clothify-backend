package by.com.clothify.clothes.repository;

import by.com.clothify.clothes.model.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    Optional<Clothes> findByIdAndUser_Email(Long id, String email);
}
