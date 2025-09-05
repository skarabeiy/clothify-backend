package by.com.clothify.plan.repository;

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

    @Query("""
            SELECT op FROM OutfitPlan op
            JOIN FETCH op.outfit o
            JOIN FETCH o.category
            LEFT JOIN FETCH o.outfitClothes oc
            LEFT JOIN FETCH oc.clothes c
            LEFT JOIN FETCH c.image
            WHERE op.user.email = :email
            AND op.plannedDate = :plannedDate
            """)
    List<OutfitPlan> findByPlannedDateAndEmailWithFullOutfitInfo(LocalDate plannedDate, String email);

    @Query("""
            SELECT op FROM OutfitPlan op
            JOIN FETCH op.outfit o
            LEFT JOIN FETCH o.outfitClothes oc
            LEFT JOIN FETCH oc.clothes c
            LEFT JOIN FETCH c.image
            WHERE op.user.email = :email
            AND op.plannedDate BETWEEN :startOfWeek AND :endOfWeek
            ORDER BY op.plannedDate
            """)
    List<OutfitPlan> findByWeekAndEmail(LocalDate startOfWeek, LocalDate endOfWeek, String email);

    @Query("""
            SELECT op FROM OutfitPlan op
            JOIN FETCH op.outfit o
            LEFT JOIN FETCH o.outfitClothes oc
            LEFT JOIN FETCH oc.clothes c
            LEFT JOIN FETCH c.image
            WHERE op.user.email = :email
            AND op.plannedDate BETWEEN :startDate AND :endDate
            
            AND op.id IN (
                SELECT MIN(op2.id) FROM OutfitPlan op2
                WHERE op2.user.email = :email
                AND op2.plannedDate BETWEEN :startDate AND :endDate
                GROUP BY op2.plannedDate
            )
            ORDER BY op.plannedDate
            """)
    List<OutfitPlan> findByDatePeriodAndEmail(LocalDate startDate, LocalDate endDate, String email);

    void deleteByIdAndUser_Email(Long id, String email);
}
