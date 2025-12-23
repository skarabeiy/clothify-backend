package by.com.clothify.links.controller;

import by.com.clothify.links.model.dto.LinkDto;
import by.com.clothify.links.model.dto.PatchedLinkDto;
import by.com.clothify.links.model.dto.SavedLinkDto;
import by.com.clothify.links.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
@Tag(name = "15 - Links API", description = "Operations for links")
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user link by ID",
            description = "Return user link by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<LinkDto> getLinkById(
            @Parameter(description = "Link ID", example = "1")
            @PathVariable Long id
    ) {
        return linkService.getLinkById(id);
    }

    @PostMapping
    @Operation(
            summary = "Save link",
            description = "Save user link",
            security = @SecurityRequirement(name = "Bearer")
    )
    public LinkDto saveLink(@RequestBody SavedLinkDto savedLinkDto) {
        return linkService.saveLink(savedLinkDto);
    }

    @PatchMapping
    @Operation(
            summary = "Update user link",
            description = "Update filled fields for user link",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<LinkDto> patchLink(@RequestBody PatchedLinkDto patchedLinkDto) {
        return linkService.patchLink(patchedLinkDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user link",
            description = "Delete user link by ID",
            security = @SecurityRequirement(name = "Bearer")
    )
    public ResponseEntity<Void> deleteLinkById(
            @Parameter(description = "Link ID", example = "1")
            @PathVariable Long id
    ) {
        return linkService.deleteLinkById(id);
    }
}
