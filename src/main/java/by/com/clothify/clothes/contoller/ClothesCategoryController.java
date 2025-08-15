package by.com.clothify.clothes.contoller;

import by.com.clothify.clothes.model.dto.ClothesCategoryDto;
import by.com.clothify.clothes.service.clothes.ClothesCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clothes-categories")
@RequiredArgsConstructor
@Tag(name = "Clothes Categories API", description = "Operations for clothes categories")
public class ClothesCategoryController {

    private final ClothesCategoryService clothesCategoryService;

    @GetMapping
    @Operation(
            summary = "Get user clothes categories",
            description = "Return list of clothing categories that user has",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<ClothesCategoryDto> getUserClothesCategories() {
        return clothesCategoryService.getUserClothesCategories();
    }
}
