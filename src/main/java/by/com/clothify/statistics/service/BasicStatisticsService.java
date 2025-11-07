package by.com.clothify.statistics.service;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.service.clothes.ClothesStatisticService;
import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.outfit.model.dto.OutfitClothesDto;
import by.com.clothify.statistics.model.dto.BasicStatisticDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicStatisticsService {

    private final ClothesStatisticService clothesStatisticService;

    public BasicStatisticDto getBasicStatistic() {

        final String email = AuthUtils.getEmail();

        try {
            List<ClothesDto> clothes = clothesStatisticService.getClothesByEmailForBasic(email);

            double totalCost = 0.0;
            Map<String, Integer> itemsAdditionalInfo = new LinkedHashMap<>();
            Map<String, Double> valueAdditionalInfo = new LinkedHashMap<>();
            String categoryName;
            double price;

            for (ClothesDto c : clothes) {

                if (c.getPrice() != null) {
                    price = c.getPrice();
                    totalCost = totalCost + c.getPrice();
                } else {
                    price = 0.0;
                }

                categoryName = c.getCategory().getName();

                if (itemsAdditionalInfo.containsKey(categoryName)) {
                    itemsAdditionalInfo.put(
                            categoryName,
                            itemsAdditionalInfo.get(categoryName) + 1
                    );
                } else {
                    itemsAdditionalInfo.put(categoryName, 1);
                }

                if (valueAdditionalInfo.containsKey(categoryName)) {
                    valueAdditionalInfo.put(
                            categoryName,
                            valueAdditionalInfo.get(categoryName) + price
                    );
                } else {
                    valueAdditionalInfo.put(categoryName, price);
                }
            }

            BasicStatisticDto.ItemsDto itemsStatistic = BasicStatisticDto.ItemsDto.builder()
                    .totalCount(clothes.size())
                    .additionalInfo(itemsAdditionalInfo)
                    .build();

            BasicStatisticDto.ValueDto valueStatistic = BasicStatisticDto.ValueDto.builder()
                    .totalCost(totalCost)
                    .additionalInfo(valueAdditionalInfo)
                    .build();

            return BasicStatisticDto.builder()
                    .items(itemsStatistic)
                    .value(valueStatistic)
                    .usage(getUsageStatistic(email, clothes.size()))
                    .build();

        } catch (Exception e) {
            log.error("Failed get basic clothes statistic for user: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private BasicStatisticDto.UsageDto getUsageStatistic(String email, int clothesCount) {

        Set<Long> userClothesInOutfitsIds = clothesStatisticService.getOutfitClothesByEmailForBasic(email).stream()
                .map(OutfitClothesDto::getClothingId)
                .collect(Collectors.toSet());

        return BasicStatisticDto.UsageDto.builder()
                .usageCount(userClothesInOutfitsIds.size())
                .totalCount(clothesCount)
                .build();
    }
}
