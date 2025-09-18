package by.com.clothify.collection.controller;

import by.com.clothify.collection.model.dto.CollectionClothesDto;
import by.com.clothify.collection.model.dto.CollectionClothesFilterDto;
import by.com.clothify.collection.model.dto.PatchedCollectionClothesDto;
import by.com.clothify.collection.model.dto.SavedCollectionClothesDto;
import by.com.clothify.collection.service.CollectionClothesService;
import by.com.clothify.common.model.enums.Season;
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
@RequestMapping("/collection-clothes")
@RequiredArgsConstructor
@Tag(name = "12 - Collection Clothes API", description = "Operations for collection clothes")
public class CollectionClothesController {

    private final CollectionClothesService collectionClothesService;

    @GetMapping
    @Operation(
            summary = "Get user collection clothes by filters",
            description = "Return user collection clothes by filters (collection ID, clothing brands and others) with pagination",
            security = @SecurityRequirement(name = "Bearer")
    )
    public Page<CollectionClothesDto> getFilteredCollectionClothes(

            @Parameter(description = "Collection ID", example = "1")
            @RequestParam(name = "collectionId") Long collectionId,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,

            @Parameter(description = "Items number per page")
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,

            @Parameter(description = "Packed status", example = "null")
            @RequestParam(name = "packed", required = false) Boolean packed,

            @Parameter(description = "List of clothes colors to filter")
            @RequestParam(name = "colors", required = false) Set<String> colors,

            @Parameter(description = "List of clothes brand IDs to filter")
            @RequestParam(name = "brandIds", required = false) Set<Long> brandIds,

            @Parameter(description = "Season to filter", example = "null")
            @RequestParam(name = "season", required = false) Season season,

            @Parameter(description = "List of clothes names to filter")
            @RequestParam(name = "names", required = false) Set<String> names
    ) {

        CollectionClothesFilterDto filter = CollectionClothesFilterDto.builder()
                .collectionId(collectionId)
                .packed(packed)
                .colors(colors)
                .brandIds(brandIds)
                .season(season)
                .names(names)
                .build();

        return collectionClothesService.getFilteredCollectionClothes(filter, page, size);
    }

    @PostMapping
    @Operation(
            summary = "Save collection clothes",
            description = "Save user collection clothes",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> saveCollectionClothes(
            @RequestBody SavedCollectionClothesDto savedCollectionClothesDto
    ) {
        return collectionClothesService.saveCollectionClothes(savedCollectionClothesDto);
    }

    @PatchMapping
    @Operation(
            summary = "Update user collection clothes",
            description = "Update filled fields for user collection clothes",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> patchCollectionClothes(
            @RequestBody PatchedCollectionClothesDto patchedCollectionClothesDto
    ) {
        return collectionClothesService.patchCollectionClothes(patchedCollectionClothesDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user collection clothes",
            description = "Delete user collection clothes by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> deleteCollectionClothesById(
            @Parameter(description = "Collection Clothes ID", example = "1")
            @PathVariable Long id
    ) {
        return collectionClothesService.deleteCollectionClothesById(id);
    }

    @GetMapping("/names")
    @Operation(
            summary = "Get user collection clothes names",
            description = "Return all user collection clothes names",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<String> getCollectionClothesNames() {
        return collectionClothesService.getCollectionClothesNames();
    }
}
