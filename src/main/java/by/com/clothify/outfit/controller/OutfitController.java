package by.com.clothify.outfit.controller;

import by.com.clothify.outfit.model.dto.OutfitDto;
import by.com.clothify.outfit.model.dto.SavedOutfitDto;
import by.com.clothify.outfit.service.OutfitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outfits")
@RequiredArgsConstructor
@Tag(name = "Outfit API", description = "Operations for outfits")
public class OutfitController {

    private final OutfitService outfitService;

    @PostMapping
    @Operation(
            summary = "Save outfit",
            description = "Save user outfit",
            security = @SecurityRequirement(name = "Bearer")
    )
    public OutfitDto saveOutfit(@RequestBody SavedOutfitDto savedOutfitDto) {
        return outfitService.saveOutfit(savedOutfitDto);
    }
}
