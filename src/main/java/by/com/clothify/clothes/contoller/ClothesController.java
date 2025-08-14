package by.com.clothify.clothes.contoller;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.model.dto.FileUploadRequest;
import by.com.clothify.clothes.service.ClothesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clothes")
@RequiredArgsConstructor
@Tag(name = "Clothes API", description = "Operations for clothes")
public class ClothesController {

    private final ClothesService clothesService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user clothes by ID",
            description = "Return users clothes by clothes ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<ClothesDto> getClothesById(
            @Parameter(description = "Clothe ID", example = "1")
            @PathVariable Long id
    ) {
        return clothesService.getClothesById(id);
    }

    @GetMapping("/by-category")
    @Operation(
            summary = "Get user clothes by category",
            description = "Return user clothes by category ID with pagination",
            security = @SecurityRequirement(name = "Bearer")
    )
    public Page<ClothesDto> getClothesByCategoryId(
            @Parameter(description = "If the category ID is not specified, all the clothes will be returned. " +
                    "If specified, only the clothes that belong to the specified category.", example = "null")
            @RequestParam(name = "categoryId", required = false) Long categoryId,

            @Parameter(description = "Page number", example = "0")
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,

            @Parameter(description = "Items number per page")
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {

        return clothesService.getClothesByCategoryId(categoryId, page, size);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "",
            description = "",
            security = @SecurityRequirement(name = "Bearer")
    )
    public void save(
            @Parameter(description = "")
            @ModelAttribute FileUploadRequest request
    ) {
        System.out.println(1);
    }
}
