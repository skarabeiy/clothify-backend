package by.com.clothify.statistics.service;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.service.clothes.ClothesStatisticService;
import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.outfit.model.dto.OutfitClothesDto;
import by.com.clothify.statistics.model.dto.AdvancedStatisticDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvancedStatisticsService {

    private final ClothesStatisticService clothesStatisticService;

    public AdvancedStatisticDto getAdvancedStatistic() {

        final String email = AuthUtils.getEmail();

        try {

            List<ClothesDto> userClothes = clothesStatisticService.getClothesByEmailForAdvanced(email);
            List<OutfitClothesDto> outfitClothes = clothesStatisticService.getOutfitClothesByEmail(email);

            Map<Long, Integer> clothesUsages = new HashMap<>();
            Long clothingId;

            for (OutfitClothesDto oc : outfitClothes) {

                clothingId = oc.getClothingId();

                if (clothesUsages.containsKey(clothingId)) {
                    clothesUsages.put(
                            clothingId,
                            clothesUsages.get(clothingId) + 1
                    );
                } else {
                    clothesUsages.put(clothingId, 1);
                }
            }

            for (ClothesDto c : userClothes) {

                if (!clothesUsages.containsKey(c.getId())) {
                    clothesUsages.put(c.getId(), 0);
                }
            }

            List<Long> clothingIdSortedByUsages = clothesUsages.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                    .map(Map.Entry::getKey)
                    .toList();

            List<ClothesDto> mostWornClothes = new ArrayList<>();
            List<ClothesDto> leastWornClothes = new ArrayList<>();

            return AdvancedStatisticDto.builder()
                    .mostWornItems(mostWornClothes)
                    .leastWornItems(leastWornClothes)
                    .build();

        } catch (Exception e) {
            log.error("Failed get advanced statistic for user: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
