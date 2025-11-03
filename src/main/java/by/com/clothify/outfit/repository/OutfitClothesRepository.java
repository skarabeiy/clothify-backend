package by.com.clothify.outfit.repository;

import by.com.clothify.outfit.model.entity.OutfitClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutfitClothesRepository extends JpaRepository<OutfitClothes, Long> {

    @Query("""
            SELECT oc FROM OutfitClothes oc
            JOIN FETCH oc.clothes c
            JOIN FETCH c.image
            WHERE oc.outfit.id = :outfitId
            """)
    List<OutfitClothes> findAllByOutfitId(Long outfitId);

    @Query("""
            SELECT oc FROM OutfitClothes oc
            JOIN FETCH oc.clothes c
            JOIN FETCH c.user u
            WHERE u.email = :email
            """)
    List<OutfitClothes> findAllByEmail(String email);
}
