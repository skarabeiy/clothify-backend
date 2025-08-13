package by.com.clothify.clothes.contoller;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.service.ClothesService;
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
}
