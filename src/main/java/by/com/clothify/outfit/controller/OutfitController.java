package by.com.clothify.outfit.controller;

import by.com.clothify.outfit.model.dto.OutfitDto;
import by.com.clothify.outfit.model.dto.SavedOutfitDto;
import by.com.clothify.outfit.service.OutfitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
