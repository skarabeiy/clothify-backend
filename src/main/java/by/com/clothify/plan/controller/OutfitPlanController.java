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
@Tag(name = "Outfit plan API", description = "Operations for outfit plans")
public class OutfitPlanController {

    private final OutfitPlanService outfitPlanService;

    @GetMapping("/{time-period}")
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
