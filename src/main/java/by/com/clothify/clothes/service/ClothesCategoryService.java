package by.com.clothify.clothes.service;

import by.com.clothify.clothes.mapper.ClothesCategoryMapper;
import by.com.clothify.clothes.model.dto.ClothesCategoryDto;
import by.com.clothify.clothes.model.entity.ClothesCategory;
import by.com.clothify.clothes.repository.ClothesCategoryRepository;
import by.com.clothify.common.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClothesCategoryService {

    private final ClothesCategoryRepository clothesCategoryRepository;

    public List<ClothesCategoryDto> getUserClothesCategories() {

        String email = AuthUtils.getEmail();
        try {
            List<ClothesCategory> categories = clothesCategoryRepository.findUserCategories(email);

            List<ClothesCategoryDto> res = categories.stream()
                    .map(ClothesCategoryMapper::mapToCategoryDto)
                    .collect(Collectors.toList());

            res.addFirst(
                    ClothesCategoryDto.builder()
                            .id(null)
                            .name("All")
                            .build()
            );

            return res;

        } catch (Exception e) {
            log.error("Failed get clothes categories for: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
