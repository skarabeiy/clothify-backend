package by.com.clothify.statistics.controller;

import by.com.clothify.statistics.model.dto.AdvancedStatisticDto;
import by.com.clothify.statistics.model.dto.BasicStatisticDto;
import by.com.clothify.statistics.service.AdvancedStatisticsService;
import by.com.clothify.statistics.service.BasicStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: кэш для статистики - хорошая идея
@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Tag(name = "13 - Statistic API", description = "Operations for statistics on user's clothing")
public class StatisticsController {

    private final BasicStatisticsService basicStatisticsService;
    private final AdvancedStatisticsService advancedStatisticsService;

    @GetMapping("/basic")
    @Operation(
            summary = "Return basic statistics",
            description = "Return basic statistics on user's clothing",
            security = @SecurityRequirement(name = "Bearer")
    )
    public BasicStatisticDto getBasicStatistic() {
        return basicStatisticsService.getBasicStatistic();
    }

    @GetMapping("/advanced")
    @Operation(
            summary = "Return advanced statistics",
            description = "Return advanced statistics on user's clothing",
            security = @SecurityRequirement(name = "Bearer")
    )
    public AdvancedStatisticDto getAdvancedStatistic() {
        return advancedStatisticsService.getAdvancedStatistic();
    }
}
