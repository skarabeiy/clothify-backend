package by.com.clothify.plan.model.repository;

import by.com.clothify.plan.model.entity.OutfitPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OutfitPlanRepository extends JpaRepository<OutfitPlan, Long> {

    @Query("""
            SELECT op FROM OutfitPlan op
            JOIN FETCH op.outfit o
            LEFT JOIN FETCH o.outfitClothes oc
            LEFT JOIN FETCH oc.clothes c
            LEFT JOIN FETCH c.image
            WHERE op.user.email = :email
            AND op.plannedDate = :plannedDate
            """)
    List<OutfitPlan> findByPlannedDateAndEmail(LocalDate plannedDate, String email);

    void deleteByIdAndUser_Email(Long id, String email);
}
