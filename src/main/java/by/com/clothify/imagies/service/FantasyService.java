package by.com.clothify.imagies.service;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.model.entity.Clothes;
import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.imagies.model.dto.FantasyDto;
import by.com.clothify.outfit.model.entity.Outfit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static by.com.clothify.clothes.mapper.ClothesMapper.mapToClothesWithOutfitsDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class FantasyService {

    public ResponseEntity<FantasyDto> getFantasyById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
//            Optional<Clothes> optionalClothes = clothesRepository.findByIdAndEmail(id, email);
//
//            if (optionalClothes.isEmpty()) {
//                log.error("Can't find clothes by id = {} for user: {}", id, email);
//                return ResponseEntity.notFound().build();
//            }
//
//            List<Long> outfitIds = outfitRepository.findAllByClothingId(optionalClothes.get().getId());
//            List<Outfit> outfits = outfitRepository.findOutfitsByIds(outfitIds);
//
//            return ResponseEntity.ok(mapToClothesWithOutfitsDto(optionalClothes.get(), outfits));
            return null;
        } catch (Exception e) {
            log.error("Failed get clothes by id = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
