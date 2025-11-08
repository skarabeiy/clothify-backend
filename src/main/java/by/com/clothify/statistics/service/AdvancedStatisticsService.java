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

    private static final Integer ITEMS_NUMBER = 3;

    private final ClothesStatisticService clothesStatisticService;

    public AdvancedStatisticDto getAdvancedStatistic() {

        final String email = AuthUtils.getEmail();

        try {

            List<ClothesDto> userClothes = clothesStatisticService.getClothesByEmailForAdvanced(email);
            List<OutfitClothesDto> outfitClothes = clothesStatisticService.getOutfitClothesByEmail(email);

            Map<Long, Integer> clothesUsages = getClothesUsages(userClothes, outfitClothes);

            List<Long> clothingIdSortedByUsages = clothesUsages.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                    .map(Map.Entry::getKey)
                    .toList();

            return AdvancedStatisticDto.builder()
                    .mostWornItems(getMostWornClothes(clothingIdSortedByUsages, userClothes))
                    .leastWornItems(getLeastWornClothes(clothingIdSortedByUsages, userClothes))
                    .build();

        } catch (Exception e) {
            log.error("Failed get advanced statistic for user: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static Map<Long, Integer> getClothesUsages(List<ClothesDto> userClothes,
                                                       List<OutfitClothesDto> outfitClothes) {

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

        return clothesUsages;
    }

    private static List<ClothesDto> getMostWornClothes(List<Long> clothingIdSortedByUsages,
                                                       List<ClothesDto> userClothes) {

        List<ClothesDto> mostWornClothes = new ArrayList<>();

        for (int i = 0; i < ITEMS_NUMBER; i++) {

            mostWornClothes.add(findClothingById(clothingIdSortedByUsages.get(i), userClothes));
        }

        return mostWornClothes;
    }

    private static List<ClothesDto> getLeastWornClothes(List<Long> clothingIdSortedByUsages,
                                                        List<ClothesDto> userClothes) {

        List<ClothesDto> leastWornClothes = new ArrayList<>();

        for (int i = userClothes.size() - 1; i > userClothes.size() - 1 - ITEMS_NUMBER; i--) {

            leastWornClothes.add(findClothingById(clothingIdSortedByUsages.get(i), userClothes));
        }

        return leastWornClothes;
    }

    private static ClothesDto findClothingById(Long clothingId, List<ClothesDto> clothes) {

        return clothes.stream()
                .filter(c -> c.getId().equals(clothingId))
                .findFirst()
                .orElseThrow();
    }
}
