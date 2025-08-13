package by.com.clothify.clothes.service;

import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.model.entity.Clothes;
import by.com.clothify.clothes.repository.ClothesRepository;
import by.com.clothify.security.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.com.clothify.clothes.mapper.ClothesMapper.mapToClothesDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClothesService {

    private final ClothesRepository clothesRepository;

    public ResponseEntity<ClothesDto> getClothesById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Clothes> optionalClothes = clothesRepository.findByIdAndUser_Email(id, email);

            if (optionalClothes.isEmpty()) {
                log.error("Can't find clothes by id = {} for user: {}", id, email);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(mapToClothesDto(optionalClothes.get()));

        } catch (Exception e) {
            log.error("Failed get clothes by id = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
