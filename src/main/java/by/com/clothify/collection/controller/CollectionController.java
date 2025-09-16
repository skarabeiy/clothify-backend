package by.com.clothify.collection.controller;

import by.com.clothify.collection.model.dto.CollectionDto;
import by.com.clothify.collection.model.dto.PatchedCollectionDto;
import by.com.clothify.collection.model.dto.SavedCollectionDto;
import by.com.clothify.collection.model.enums.CollectionType;
import by.com.clothify.collection.service.CollectionService;
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

@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
@Tag(name = "Collections API", description = "Operations for collections")
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    @Operation(
            summary = "Get user collections by type",
            description = "Return user collections by type with pagination",
            security = @SecurityRequirement(name = "Bearer")
    )
    public Page<CollectionDto> getCollectionsByType(
            @Parameter(description = "If the type is not specified, all the collections will be returned. " +
                    "If specified, only the collections that belong to the specified type.", example = "null")
            @RequestParam(name = "type", required = false) CollectionType type,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,

            @Parameter(description = "Items number per page")
            @RequestParam(name = "size", required = false, defaultValue = "8") int size
    ) {
        return collectionService.getCollectionsByType(type, page, size);
    }

    @PostMapping
    @Operation(
            summary = "Save collection",
            description = "Save user collection",
            security = @SecurityRequirement(name = "Bearer")
    )
    public CollectionDto saveCollection(@RequestBody SavedCollectionDto savedCollectionDto) {
        return collectionService.saveCollection(savedCollectionDto);
    }

    @PatchMapping
    @Operation(
            summary = "Update user collection",
            description = "Update filled fields for user collection",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<CollectionDto> patchCollection(@RequestBody PatchedCollectionDto patchedCollectionDto) {
        return collectionService.patchCollection(patchedCollectionDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user collection",
            description = "Delete user collection by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> deleteCollectionById(
            @Parameter(description = "Collection ID", example = "1")
            @PathVariable Long id
    ) {
        return collectionService.deleteCollectionById(id);
    }
}
