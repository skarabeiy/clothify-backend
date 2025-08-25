package by.com.clothify.outfit.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.outfit.mapper.OutfitMapper;
import by.com.clothify.outfit.model.dto.OutfitDto;
import by.com.clothify.outfit.model.dto.SavedOutfitDto;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.outfit.model.entity.OutfitClothes;
import by.com.clothify.outfit.repository.OutfitClothesRepository;
import by.com.clothify.outfit.repository.OutfitRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutfitService {

    private final UserRepository userRepository;
    private final OutfitRepository outfitRepository;
    private final OutfitClothesRepository outfitClothesRepository;

    @Transactional
    public OutfitDto saveOutfit(SavedOutfitDto savedOutfitDto) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();

            Outfit outfit = OutfitMapper.mapToOutfit(savedOutfitDto);
            outfit.setUser(user);

            LocalDateTime now = LocalDateTime.now();
            outfit.setCreatedAt(now);
            outfit.setUpdatedAt(now);

            outfit = outfitRepository.save(outfit);

            List<OutfitClothes> outfitClothes = savedOutfitDto.getClothes().stream()
                    .map(clothes -> OutfitClothes.builder()
                            .build())
                    .toList();

            outfitClothesRepository.saveAll(outfitClothes);

            return null;

        } catch (Exception e) {
            log.error("Failed save outfit: {} for user: {}", savedOutfitDto.getName(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
