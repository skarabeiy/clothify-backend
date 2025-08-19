package by.com.clothify.clothes.repository;

import by.com.clothify.clothes.model.entity.ClothesBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesBrandRepository extends JpaRepository<ClothesBrand, Long> {

    @Query("SELECT b FROM ClothesBrand b " +
            "LEFT JOIN b.user u " +
            "WHERE b.isCustom is FALSE " +
            "OR u.email = :email")
    List<ClothesBrand> findUserClothesBrand(String email);
}
