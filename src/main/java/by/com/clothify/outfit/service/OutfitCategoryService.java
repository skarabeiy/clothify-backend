package by.com.clothify.outfit.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.outfit.mapper.OutfitCategoryMapper;
import by.com.clothify.outfit.model.dto.OutfitCategoryDto;
import by.com.clothify.outfit.model.entity.OutfitCategory;
import by.com.clothify.outfit.repository.OutfitCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutfitCategoryService {

    private final OutfitCategoryRepository outfitCategoryRepository;

    @Cacheable(value = "getAllOutfitCategories")
    public List<OutfitCategoryDto> getAllOutfitCategories() {

        try {
            List<OutfitCategory> categories = outfitCategoryRepository.findAllByOrderByPosition();
            log.info("Get all outfit categories");

            return mapToCategoryDtoList(categories);

        } catch (Exception e) {
            log.error("Failed get all outfit categories");
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<OutfitCategoryDto> getUserOutfitCategories() {

        final String email = AuthUtils.getEmail();

        try {
            List<OutfitCategory> categories = outfitCategoryRepository.findUserCategories(email);

            List<OutfitCategoryDto> res = mapToCategoryDtoList(categories);

            res.addFirst(
                    OutfitCategoryDto.builder()
                            .id(null)
                            .name("All")
                            .build()
            );

            return res;

        } catch (Exception e) {
            log.error("Failed get outfit categories for: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static List<OutfitCategoryDto> mapToCategoryDtoList(List<OutfitCategory> categories) {

        return categories.stream()
                .map(OutfitCategoryMapper::mapToCategoryDto)
                .collect(Collectors.toList());
    }
}
