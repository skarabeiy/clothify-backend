package by.com.clothify.plan.mapper;

import by.com.clothify.common.util.DateUtils;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.plan.model.dto.SavedOutfitPlanDto;
import by.com.clothify.plan.model.entity.OutfitPlan;
import by.com.clothify.security.model.entity.User;

public class OutfitPlanMapper {

    public static OutfitPlan mapToOutfitPlan(SavedOutfitPlanDto savedOutfitPlanDto, Long userId) {

        return OutfitPlan.builder()
                .plannedDate(DateUtils.covertToLocaleDate(savedOutfitPlanDto.getDate()))
                .outfit(
                        Outfit.builder()
                                .id(savedOutfitPlanDto.getOutfitId())
                                .build()
                )
                .user(
                        User.builder()
                                .id(userId)
                                .build()
                )
                .build();
    }
}
