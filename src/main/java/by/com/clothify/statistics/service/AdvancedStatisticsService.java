package by.com.clothify.statistics.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.outfit.repository.OutfitClothesRepository;
import by.com.clothify.statistics.model.dto.AdvancedStatisticDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvancedStatisticsService {

    private final OutfitClothesRepository outfitClothesRepository;

    public AdvancedStatisticDto getAdvancedStatistic() {

        final String email = AuthUtils.getEmail();

        try {
            var res = outfitClothesRepository.findAllByEmailWithSorting(email);
            return null;

        } catch (Exception e) {
            log.error("Failed get advanced statistic for user: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
