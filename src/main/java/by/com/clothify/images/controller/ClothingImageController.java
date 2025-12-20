package by.com.clothify.images.controller;

import by.com.clothify.common.model.enums.Season;
import by.com.clothify.images.model.dto.ClothingImageDto;
import by.com.clothify.images.model.dto.ClothingImageFilterDto;
import by.com.clothify.images.model.dto.PatchedClothingImageDto;
import by.com.clothify.images.model.dto.UploadedClothingImageDto;
import by.com.clothify.images.service.ClothingImageService;
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
@RequestMapping("/images")
@RequiredArgsConstructor
@Tag(name = "14 - Images API", description = "Operations for images")
public class ClothingImageController {

    private final ClothingImageService clothingImageService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user image by ID",
            description = "Return user image by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<ClothingImageDto> getClothingImageById(
            @Parameter(description = "Image ID", example = "1")
            @PathVariable Long id
    ) {
        return clothingImageService.getClothingImageById(id);
    }

    @GetMapping
    @Operation(
            summary = "Get user images by filters",
            description = "Return user images by filters (category ID, colors and others) with pagination",
            security = @SecurityRequirement(name = "Bearer")
    )
    public Page<ClothingImageDto> getFilteredClothingImages(
            @Parameter(description = "If the category ID is not specified, all the images will be returned. " +
                    "If specified, only the images that belong to the specified category.", example = "null")
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

            @Parameter(description = "List of images names to filter")
            @RequestParam(name = "names", required = false) Set<String> names
    ) {

        ClothingImageFilterDto filter = ClothingImageFilterDto.builder()
                .categoryId(categoryId)
                .colors(colors)
                .brandIds(brandIds)
                .season(season)
                .names(names)
                .build();

        return clothingImageService.getFilteredClothingImages(filter, page, size);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Save user image",
            description = "Accepts multipart/form-data and save image",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ClothingImageDto saveClothingImage(
            @Parameter(description = "Image that need to be saved")
            @ModelAttribute UploadedClothingImageDto uploadedImageDto
    ) {
        return clothingImageService.saveClothingImage(uploadedImageDto);
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "SWAGGER WORK BADLY. Update user image",
            description = "SWAGGER WORK BADLY. Update filled fields for user image",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<ClothingImageDto> patchClothingImage(
            @Parameter(description = "SWAGGER WORK BADLY. Image that need to be update")
            @ModelAttribute PatchedClothingImageDto patchedImageDto
    ) {
        return clothingImageService.patchClothingImage(patchedImageDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user image",
            description = "Delete user image by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> deleteClothingImageById(
            @Parameter(description = "Image ID", example = "1")
            @PathVariable Long id
    ) {
        return clothingImageService.deleteClothingImageById(id);
    }

    @GetMapping("/names")
    @Operation(
            summary = "Get user images names",
            description = "Return all user images names",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<String> getClothingImagesNames() {
        return clothingImageService.getClothingImagesNames();
    }
}
