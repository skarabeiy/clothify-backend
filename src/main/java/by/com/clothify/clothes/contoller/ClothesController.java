package by.com.clothify.clothes.contoller;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.model.dto.ClothesFilterDto;
import by.com.clothify.clothes.model.dto.PatchedClothesDto;
import by.com.clothify.clothes.model.dto.UploadedClothesDto;
import by.com.clothify.clothes.service.clothes.ClothesService;
import by.com.clothify.common.model.enums.Season;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/clothes")
@RequiredArgsConstructor
@Tag(name = "05 - Clothes API", description = "Operations for clothes")
public class ClothesController {

    private final ClothesService clothesService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user clothes by ID",
            description = "Return user clothes by clothes ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<ClothesDto> getClothesById(
            @Parameter(description = "Clothe ID", example = "1")
            @PathVariable Long id
    ) {
        return clothesService.getClothesById(id);
    }

    @GetMapping
    @Operation(
            summary = "Get user clothes by filters",
            description = "Return user clothes by filters (category ID, colors and others) with pagination",
            security = @SecurityRequirement(name = "Bearer")
    )
    public Page<ClothesDto> getFilteredClothes(
            @Parameter(description = "If the category ID is not specified, all the clothes will be returned. " +
                    "If specified, only the clothes that belong to the specified category.", example = "null")
            @RequestParam(name = "categoryId", required = false) Long categoryId,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,

            @Parameter(description = "Items number per page")
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,

            @Parameter(description = "List of colors to filter")
            @RequestParam(name = "colors", required = false) Set<String> colors,

            @Parameter(description = "List of brand IDs to filter")
            @RequestParam(name = "brandIds", required = false) Set<Long> brandIds,

            @Parameter(description = "Season to filter", example = "null")
            @RequestParam(name = "season", required = false) Season season,

            @Parameter(description = "List of clothes names to filter")
            @RequestParam(name = "names", required = false) Set<String> names,

            @Parameter(description = "Use in outfits", example = "null")
            @RequestParam(name = "usedInOutfits", required = false) Boolean usedInOutfits
    ) {

        ClothesFilterDto filter = ClothesFilterDto.builder()
                .categoryId(categoryId)
                .colors(colors)
                .brandIds(brandIds)
                .season(season)
                .names(names)
                .usedInOutfits(usedInOutfits)
                .build();

        return clothesService.getFilteredClothes(filter, page, size);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Save user clothes",
            description = "Accepts multipart/form-data and save clothes",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ClothesDto saveClothes(
            @Parameter(description = "Clothes that need to be saved")
            @ModelAttribute UploadedClothesDto uploadedClothes
    ) {
        return clothesService.saveClothes(uploadedClothes);
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "SWAGGER WORK BADLY. Update user clothes",
            description = "SWAGGER WORK BADLY. Update filled fields for user clothes",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<ClothesDto> patchClothes(
            @Parameter(description = "SWAGGER WORK BADLY. Clothes that need to be update")
            @ModelAttribute PatchedClothesDto patchedClothes
    ) {
        return clothesService.patchClothes(patchedClothes);
    }

    // удалять CollectionClothes
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user clothes",
            description = "Delete user clothes by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> deleteClothesById(
            @Parameter(description = "Clothe ID", example = "1")
            @PathVariable Long id
    ) {
        return clothesService.deleteClothesById(id);
    }

    @GetMapping("/names")
    @Operation(
            summary = "Get user clothes names",
            description = "Return all user clothes names",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<String> getClothesNames() {
        return clothesService.getClothesNames();
    }
}
