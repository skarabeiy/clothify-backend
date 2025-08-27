package by.com.clothify.outfit.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.outfit.mapper.OutfitClothesMapper;
import by.com.clothify.outfit.mapper.OutfitMapper;
import by.com.clothify.outfit.model.dto.OutfitDto;
import by.com.clothify.outfit.model.dto.OutfitFilterDto;
import by.com.clothify.outfit.model.dto.SavedOutfitDto;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.outfit.model.entity.OutfitClothes;
import by.com.clothify.outfit.repository.OutfitClothesRepository;
import by.com.clothify.outfit.repository.OutfitRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutfitService {

    private final UserRepository userRepository;
    private final OutfitRepository outfitRepository;
    private final OutfitClothesRepository outfitClothesRepository;

    public ResponseEntity<OutfitDto> getOutfitById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Outfit> optionalOutfit = outfitRepository.findByIdAndUser_Email(id, email);

            if (optionalOutfit.isEmpty()) {
                log.error("Can't find outfit by id = {} for user: {}", id, email);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(OutfitMapper.mapToOutfitDto(optionalOutfit.get()));

        } catch (Exception e) {
            log.error("Failed get outfit by id = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Page<OutfitDto> getFilteredOutfits(OutfitFilterDto filter, int page, int size) {

        final String email = AuthUtils.getEmail();

        try {
            Pageable pageable = PageRequest.of(page, size);

            return outfitRepository.findFilteredOutfits(email, pageable, filter)
                    .map(OutfitMapper::mapToOutfitDto);

        } catch (Exception e) {
            log.error("Failed get outfits by category id = {}, for user: {}", filter.getCategoryId(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public OutfitDto saveOutfit(SavedOutfitDto savedOutfitDto) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();

            Outfit outfit = createOutfit(savedOutfitDto, user);

            outfit = outfitRepository.save(outfit);
            final Long outfitId = outfit.getId();

            List<OutfitClothes> outfitClothes = savedOutfitDto.getClothes().stream()
                    .map(outfitClothesDto -> OutfitClothesMapper.mapToOutfitClothes(outfitClothesDto, outfitId))
                    .toList();

            outfitClothes = outfitClothesRepository.saveAll(outfitClothes);

            return OutfitMapper.mapToOutfitDto(outfit, outfitClothes);

        } catch (Exception e) {
            log.error("Failed save outfit: {} for user: {}", savedOutfitDto.getName(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static Outfit createOutfit(SavedOutfitDto savedOutfitDto, User user) {

        Outfit outfit = OutfitMapper.mapToOutfit(savedOutfitDto);
        outfit.setUser(user);

        LocalDateTime now = LocalDateTime.now();
        outfit.setCreatedAt(now);
        outfit.setUpdatedAt(now);

        return outfit;
    }

    @Transactional
    public ResponseEntity<Void> deleteOutfitById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            outfitRepository.deleteByIdAndUser_Email(id, email);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed delete outfit with ID = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<String> getOutfitNames() {

        final String email = AuthUtils.getEmail();

        try {
            return outfitRepository.findOutfitNames(email).stream()
                    .distinct()
                    .toList();

        } catch (Exception e) {
            log.error("Failed get outfit names for user: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
