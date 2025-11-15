package by.com.clothify.imagies.controller;

import by.com.clothify.imagies.model.dto.FantasyDto;
import by.com.clothify.imagies.service.FantasyService;
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
@RequestMapping("/imagies")
@RequiredArgsConstructor
@Tag(name = "14 - Imagies API", description = "Operations for imagies")
public class FantasyController {

    private final FantasyService fantasyService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get fantasy by ID",
            description = "Return user fantasy by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<FantasyDto> getFantasyById(
            @Parameter(description = "Fantasy ID", example = "1")
            @PathVariable Long id
    ) {
        return fantasyService.getFantasyById(id);
    }
}
