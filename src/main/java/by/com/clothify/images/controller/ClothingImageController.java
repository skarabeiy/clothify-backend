package by.com.clothify.images.controller;

import by.com.clothify.images.model.dto.ClothingImageDto;
import by.com.clothify.images.service.ClothingImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Tag(name = "14 - Images API", description = "Operations for images")
public class ClothingImageController {

    private final ClothingImageService clothingImageService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get image by ID",
            description = "Return user image by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<ClothingImageDto> getClothingImageById(
            @Parameter(description = "Image ID", example = "1")
            @PathVariable Long id
    ) {
        return clothingImageService.getClothingImageById(id);
    }
}
