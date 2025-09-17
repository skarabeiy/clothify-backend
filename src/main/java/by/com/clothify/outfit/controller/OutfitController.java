package by.com.clothify.outfit.controller;

import by.com.clothify.common.model.enums.Season;
import by.com.clothify.outfit.model.dto.OutfitDto;
import by.com.clothify.outfit.model.dto.OutfitFilterDto;
import by.com.clothify.outfit.model.dto.PatchedOutfitDto;
import by.com.clothify.outfit.model.dto.SavedOutfitDto;
import by.com.clothify.outfit.service.OutfitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/outfits")
@RequiredArgsConstructor
@Tag(name = "08 - Outfit API", description = "Operations for outfits")
public class OutfitController {

    private final OutfitService outfitService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user outfit by ID",
            description = "Return user outfit by outfit ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<OutfitDto> getOutfitById(
            @Parameter(description = "Outfit ID", example = "1")
            @PathVariable Long id
    ) {
        return outfitService.getOutfitById(id);
    }

    @GetMapping
    @Operation(
            summary = "Get user outfits by filters",
            description = "Return user outfits by filters (category ID, season and others) with pagination",
            security = @SecurityRequirement(name = "Bearer")
    )
    public Page<OutfitDto> getFilteredOutfits(
            @Parameter(description = "If the category ID is not specified, all the outfits will be returned. " +
                    "If specified, only the outfits that belong to the specified category.", example = "null")
            @RequestParam(name = "categoryId", required = false) Long categoryId,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,

            @Parameter(description = "Items number per page")
            @RequestParam(name = "size", required = false, defaultValue = "8") int size,

            @Parameter(description = "List of clothing brand IDs to filter")
            @RequestParam(name = "brandIds", required = false) Set<Long> brandIds,

            @Parameter(description = "Season to filter", example = "null")
            @RequestParam(name = "season", required = false) Season season,

            @Parameter(description = "List of outfits names to filter")
            @RequestParam(name = "names", required = false) Set<String> names
    ) {

        OutfitFilterDto filter = OutfitFilterDto.builder()
                .categoryId(categoryId)
                .clothingBrandIds(brandIds)
                .season(season)
                .names(names)
                .build();

        return outfitService.getFilteredOutfits(filter, page, size);
    }

    @PostMapping
    @Operation(
            summary = "Save outfit",
            description = "Save user outfit",
            security = @SecurityRequirement(name = "Bearer")
    )
    public OutfitDto saveOutfit(@RequestBody SavedOutfitDto savedOutfitDto) {
        return outfitService.saveOutfit(savedOutfitDto);
    }

    @PatchMapping
    @Operation(
            summary = "Update user outfit",
            description = "Update filled fields for user outfit. Also add/update/delete clothes in outfit",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> patchOutfit(@RequestBody PatchedOutfitDto patchedOutfitDto) {
        return outfitService.patchOutfit(patchedOutfitDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user outfit",
            description = "Delete user outfit by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> deleteOutfitById(
            @Parameter(description = "Outfit ID", example = "1")
            @PathVariable Long id
    ) {
        return outfitService.deleteOutfitById(id);
    }

    @GetMapping("/names")
    @Operation(
            summary = "Get user outfit names",
            description = "Return all user outfit names",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<String> getOutfitNames() {
        return outfitService.getOutfitNames();
    }
}
