package by.com.clothify.collection.controller;

import by.com.clothify.collection.model.dto.SavedCollectionClothesDto;
import by.com.clothify.collection.service.CollectionClothesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection-clothes")
@RequiredArgsConstructor
@Tag(name = "12 - Collection Clothes API", description = "Operations for collection clothes")
public class CollectionClothesController {

    private final CollectionClothesService collectionClothesService;

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
}
