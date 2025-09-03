package by.com.clothify.plan.model.repository;

import by.com.clothify.plan.model.entity.OutfitPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutfitPlanRepository extends JpaRepository<OutfitPlan, Long> {
}
