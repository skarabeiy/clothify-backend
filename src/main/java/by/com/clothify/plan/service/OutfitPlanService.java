package by.com.clothify.plan.service;

import by.com.clothify.outfit.repository.OutfitRepository;
import by.com.clothify.plan.model.dto.OutfitPlanDto;
import by.com.clothify.plan.model.dto.SavedOutfitPlanDto;
import by.com.clothify.plan.model.enums.TimePeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutfitPlanService {

    private final OutfitRepository outfitRepository;

    public List<OutfitPlanDto> getOutfitPlansByTimePeriod(TimePeriod timePeriod) {

        return List.of();
    }

    public void addOutfitPlan(SavedOutfitPlanDto savedOutfitPlanDto) {

    }
}
