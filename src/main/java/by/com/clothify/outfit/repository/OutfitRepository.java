package by.com.clothify.outfit.repository;

import by.com.clothify.outfit.model.entity.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Long> {

    Optional<Outfit> findByIdAndUser_Email(Long id, String email);
}
