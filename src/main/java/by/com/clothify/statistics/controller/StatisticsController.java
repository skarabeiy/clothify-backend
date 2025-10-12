package by.com.clothify.statistics.controller;

import by.com.clothify.statistics.model.dto.BasicStatisticDto;
import by.com.clothify.statistics.service.StatisticsService;
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

    private final StatisticsService statisticsService;

    @GetMapping("/basic")
    @Operation(
            summary = "Return basic statistics",
            description = "Return basic statistics on user's clothing",
            security = @SecurityRequirement(name = "Bearer")
    )
    public BasicStatisticDto getBasicStatistic() {
        return statisticsService.getBasicStatistic();
    }
}
