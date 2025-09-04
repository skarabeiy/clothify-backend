package by.com.clothify.plan.mapper;

import by.com.clothify.common.util.DateUtils;
import by.com.clothify.outfit.mapper.OutfitMapper;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.plan.model.dto.OutfitPlanDto;
import by.com.clothify.plan.model.dto.SavedOutfitPlanDto;
import by.com.clothify.plan.model.entity.OutfitPlan;
import by.com.clothify.security.model.entity.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class OutfitPlanMapper {

    public static Map<String, List<OutfitPlanDto>> mapToMapOfOutfitPlanDto(List<OutfitPlan> outfitPlans) {

        return mapToListOfOutfitPlanDto(outfitPlans).stream()
                .collect(Collectors.groupingBy(
                        OutfitPlanDto::getDate,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    public static List<OutfitPlanDto> mapToListOfOutfitPlanDto(List<OutfitPlan> outfitPlans) {

        return outfitPlans.stream()
                .map(OutfitPlanMapper::mapToOutfitPlanDto)
                .toList();
    }

    public static OutfitPlanDto mapToOutfitPlanDto(OutfitPlan outfitPlan) {

        return OutfitPlanDto.builder()
                .id(outfitPlan.getId())
                .date(DateUtils.convertToString(outfitPlan.getPlannedDate()))
                .outfit(OutfitMapper.mapToPageOutfitDto(outfitPlan.getOutfit()))
                .build();
    }

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
