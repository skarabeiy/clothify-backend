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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvancedStatisticsService {

    private final ClothesStatisticService clothesStatisticService;

    public AdvancedStatisticDto getAdvancedStatistic() {

        final String email = AuthUtils.getEmail();

        try {
            List<OutfitClothesDto> outfitClothes = clothesStatisticService.getOutfitClothesByEmailForAdvanced(email);

            Map<Long, Integer> clothesFrequency = new LinkedHashMap<>();
            Long clothingId;

            for (OutfitClothesDto oc : outfitClothes) {

                clothingId = oc.getClothingId();

                if (clothesFrequency.containsKey(clothingId)) {
                    clothesFrequency.put(clothingId, clothesFrequency.get(clothingId) + 1);
                } else {
                    clothesFrequency.put(clothingId, 1);
                }
            }

            clothesFrequency = clothesFrequency.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                    .limit(3)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            List<ClothesDto> userClothes = clothesStatisticService.getClothesByEmailForAdvanced(email);

            List<ClothesDto> mostWornClothes = new ArrayList<>();
            OutfitClothesDto outfitClothesDto;

//            clothesFrequency.keySet().forEach(id -> {
            for (Long id : clothesFrequency.keySet()) {

                outfitClothesDto = outfitClothes.stream()
                        .filter(oc -> oc.getClothingId().equals(id))
                        .findFirst()
                        .get();

                mostWornClothes.add(
                        ClothesDto.builder()
                                .id(id)
                                .build()
                );
            }

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
