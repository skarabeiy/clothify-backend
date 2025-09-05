package by.com.clothify.collection.controller;

import by.com.clothify.collection.model.dto.CollectionDto;
import by.com.clothify.collection.model.dto.SavedCollectionDto;
import by.com.clothify.collection.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
@Tag(name = "Collections API", description = "Operations for collections")
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    @Operation(
            summary = "Save collection",
            description = "Save user collection",
            security = @SecurityRequirement(name = "Bearer")
    )
    public CollectionDto saveCollection(@RequestBody SavedCollectionDto savedCollectionDto) {
        return collectionService.saveCollection(savedCollectionDto);
    }
}
