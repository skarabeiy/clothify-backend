package by.com.clothify.plan.mapper;

import by.com.clothify.common.util.DateUtils;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.plan.model.dto.SavedOutfitPlanDto;
import by.com.clothify.plan.model.entity.OutfitPlan;

public class OutfitPlanMapper {

    public static OutfitPlan mapToOutfitPlan(SavedOutfitPlanDto savedOutfitPlanDto) {

        return OutfitPlan.builder()
                .plannedDate(DateUtils.covertToLocaleDate(savedOutfitPlanDto.getDate()))
                .outfit(
                        Outfit.builder()
                                .id(savedOutfitPlanDto.getOutfitId())
                                .build()
                )
                .build();
    }
}
