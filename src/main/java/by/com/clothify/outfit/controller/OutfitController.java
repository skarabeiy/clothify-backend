package by.com.clothify.outfit.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outfits")
@RequiredArgsConstructor
@Tag(name = "Outfit API", description = "Operations for outfits")
public class OutfitController {
}
