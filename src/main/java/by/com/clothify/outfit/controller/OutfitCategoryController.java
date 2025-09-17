package by.com.clothify.outfit.controller;

import by.com.clothify.outfit.model.dto.OutfitCategoryDto;
import by.com.clothify.outfit.service.OutfitCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/outfit-categories")
@RequiredArgsConstructor
@Tag(name = "09 - Outfit Categories API", description = "Operations for outfit categories")
public class OutfitCategoryController {

    private final OutfitCategoryService outfitCategoryService;

    @GetMapping
    @Operation(
            summary = "Get all outfit categories",
            description = "Return list of all outfit categories",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<OutfitCategoryDto> getAllOutfitCategories() {
        return outfitCategoryService.getAllOutfitCategories();
    }

    @GetMapping("/for-user")
    @Operation(
            summary = "Get user outfit categories",
            description = "Return list of outfit categories that user has",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<OutfitCategoryDto> getUserOutfitCategories() {
        return outfitCategoryService.getUserOutfitCategories();
    }
}
