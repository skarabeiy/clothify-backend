package by.com.clothify.links.controller;

import by.com.clothify.common.model.enums.Season;
import by.com.clothify.links.model.dto.LinkDto;
import by.com.clothify.links.model.dto.LinkFilterDto;
import by.com.clothify.links.model.dto.PatchedLinkDto;
import by.com.clothify.links.model.dto.SavedLinkDto;
import by.com.clothify.links.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping
    @Operation(
            summary = "Get user links by filters",
            description = "Return user links by filters (category ID, colors and others) with pagination",
            security = @SecurityRequirement(name = "Bearer")
    )
    public Page<LinkDto> getFilteredLinks(
            @Parameter(description = "If the category ID is not specified, all the links will be returned. " +
                    "If specified, only the links that belong to the specified category.", example = "null")
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

            @Parameter(description = "List of link names to filter")
            @RequestParam(name = "names", required = false) Set<String> names
    ) {

        LinkFilterDto filter = LinkFilterDto.builder()
                .categoryId(categoryId)
                .colors(colors)
                .brandIds(brandIds)
                .season(season)
                .names(names)
                .build();

        return linkService.getFilteredLinks(filter, page, size);
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
