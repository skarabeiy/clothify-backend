package by.com.clothify.plan.controller;

import by.com.clothify.plan.model.dto.OutfitPlanDto;
import by.com.clothify.plan.model.dto.SavedOutfitPlanDto;
import by.com.clothify.plan.model.enums.TimePeriod;
import by.com.clothify.plan.service.OutfitPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/outfit-plans")
@RequiredArgsConstructor
@Tag(name = "Outfit Plan API", description = "Operations for outfit plans")
public class OutfitPlanController {

    private final OutfitPlanService outfitPlanService;

    @GetMapping("/by-time-period/{time-period}")
    @Operation(
            summary = "Get outfit plans by time period",
            description = "Get user outfit plans by time period type",
            security = @SecurityRequirement(name = "Bearer")
    )
    public Map<String, List<OutfitPlanDto>> getOutfitPlansByTimePeriod(
            @Parameter(description = "Time period type", example = "TODAY")
            @PathVariable(name = "time-period") TimePeriod timePeriod
    ) {
        return outfitPlanService.getOutfitPlansByTimePeriod(timePeriod);
    }

    @GetMapping("/by-month/{month}/{year}")
    @Operation(
            summary = "Get monthly outfit plans",
            description = "Get user outfit plans for month",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<OutfitPlanDto> getMonthlyOutfitPlans(
            @Parameter(description = "Month number (January = 1)", example = "9")
            @PathVariable Integer month,

            @Parameter(description = "Year number", example = "2025")
            @PathVariable Integer year
    ) {
        return outfitPlanService.getMonthlyOutfitPlans(month, year);
    }

    @GetMapping("/by-date/{date}")
    @Operation(
            summary = "Get outfit plans by date",
            description = "Get user outfit plans by date",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<OutfitPlanDto> getOutfitPlansByDate(
            @Parameter(description = "Date in dd.MM.yyyy format", example = "03.09.2025")
            @PathVariable String date
    ) {
        return outfitPlanService.getOutfitPlansByDate(date);
    }

    @PostMapping
    @Operation(
            summary = "Add outfit plan",
            description = "Add user outfit plan for specified date",
            security = @SecurityRequirement(name = "Bearer")
    )
    public void addOutfitPlan(@RequestBody SavedOutfitPlanDto savedOutfitPlanDto) {
        outfitPlanService.addOutfitPlan(savedOutfitPlanDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user outfit plan",
            description = "Delete user outfit plan by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> deleteOutfitPlanById(
            @Parameter(description = "Outfit Plan ID", example = "1")
            @PathVariable Long id
    ) {
        return outfitPlanService.deleteOutfitPlanById(id);
    }
}
