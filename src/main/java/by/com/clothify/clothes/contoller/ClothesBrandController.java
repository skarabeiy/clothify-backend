package by.com.clothify.clothes.contoller;

import by.com.clothify.clothes.model.dto.ClothesBrandDto;
import by.com.clothify.clothes.model.dto.ClothesBrandRequestDto;
import by.com.clothify.clothes.service.clothes.ClothesBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clothes-brands")
@RequiredArgsConstructor
@Tag(name = "07 - Clothes Brands API", description = "Operations for clothes brands")
public class ClothesBrandController {

    private final ClothesBrandService clothesBrandService;

    @GetMapping
    @Operation(
            summary = "Get user brands",
            description = "Get common and custom user clothes brands",
            security = @SecurityRequirement(name = "Bearer")
    )
    public List<ClothesBrandDto> getUserClothesBrands() {
        return clothesBrandService.getUserClothesBrands();
    }

    @PostMapping
    @Operation(
            summary = "Save custom brand",
            description = "Save user custom brand",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ClothesBrandDto saveCustomClothesBrand(@RequestBody ClothesBrandRequestDto request) {
        return clothesBrandService.saveCustomClothesBrand(request);
    }
}
