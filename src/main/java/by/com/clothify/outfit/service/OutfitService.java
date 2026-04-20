package by.com.clothify.outfit.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.outfit.mapper.OutfitClothesMapper;
import by.com.clothify.outfit.mapper.OutfitMapper;
import by.com.clothify.outfit.model.dto.OutfitDto;
import by.com.clothify.outfit.model.dto.OutfitFilterDto;
import by.com.clothify.outfit.model.dto.PatchedOutfitClothesDto;
import by.com.clothify.outfit.model.dto.PatchedOutfitDto;
import by.com.clothify.outfit.model.dto.SavedOutfitClothesDto;
import by.com.clothify.outfit.model.dto.SavedOutfitDto;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.outfit.model.entity.OutfitCategory;
import by.com.clothify.outfit.model.entity.OutfitClothes;
import by.com.clothify.outfit.repository.OutfitClothesRepository;
import by.com.clothify.outfit.repository.OutfitRepository;
import by.com.clothify.plan.model.entity.OutfitPlan;
import by.com.clothify.plan.repository.OutfitPlanRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static by.com.clothify.outfit.mapper.OutfitMapper.mapToOutfitDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutfitService {

    private final UserRepository userRepository;
    private final OutfitRepository outfitRepository;
    private final OutfitClothesRepository outfitClothesRepository;
    private final OutfitPlanRepository outfitPlanRepository;

    public ResponseEntity<OutfitDto> getOutfitById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Outfit> optionalOutfit = outfitRepository.findByIdAndEmail(id, email);

            if (optionalOutfit.isEmpty()) {
                log.error("Can't find outfit by id = {} for user: {}", id, email);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(mapToOutfitDto(optionalOutfit.get()));

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

            Page<Long> outfitIds = outfitRepository.findFilteredOutfitIds(email, pageable, filter);

            if (outfitIds.isEmpty()) {
                return Page.empty(pageable);
            }

            List<OutfitDto> outfits = OutfitMapper.mapToListOfPageOutfitDto(
                    outfitRepository.findOutfitsByIds(outfitIds.getContent())
            );

            return new PageImpl<>(outfits, pageable, outfitIds.getTotalElements());

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

            List<OutfitClothes> outfitClothes = mapToOutfitClothesList(savedOutfitDto.getClothes(), outfit.getId());

            outfitClothesRepository.saveAll(outfitClothes);

            return mapToOutfitDto(outfit, outfitClothes);

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

    private static List<OutfitClothes> mapToOutfitClothesList(List<SavedOutfitClothesDto> dtoList, Long outfitId) {

        return dtoList.stream()
                .map(outfitClothesDto -> OutfitClothesMapper.mapToOutfitClothes(outfitClothesDto, outfitId))
                .toList();
    }

    @Transactional
    public ResponseEntity<Void> patchOutfit(PatchedOutfitDto patchedOutfitDto) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Outfit> optionalOutfit = outfitRepository.findByIdAndEmail(patchedOutfitDto.getId(), email);

            if (optionalOutfit.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Outfit outfit = optionalOutfit.get();

            setPatchedOutfitFields(outfit, patchedOutfitDto);

            outfit = outfitRepository.save(outfit);

            patchOutfitClothes(patchedOutfitDto, outfit.getOutfitClothes());
            deleteOutfitClothes(patchedOutfitDto, outfit.getOutfitClothes());
            addOutfitClothes(patchedOutfitDto, outfit);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed patch outfit with ID = {} for user: {}", patchedOutfitDto.getId(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static void setPatchedOutfitFields(Outfit outfit, PatchedOutfitDto patchedOutfitDto) {

        if (patchedOutfitDto.getName() != null) {
            outfit.setName(patchedOutfitDto.getName());
        }

        if (patchedOutfitDto.getBackgroundColor() != null) {
            outfit.setBackgroundColor(patchedOutfitDto.getBackgroundColor());
        }

        if (patchedOutfitDto.getCategoryId() != null) {
            outfit.setCategory(
                    OutfitCategory.builder()
                            .id(patchedOutfitDto.getCategoryId())
                            .build()
            );
        }

        if (patchedOutfitDto.getSeason() != null) {
            outfit.setSeason(patchedOutfitDto.getSeason());
        }

        outfit.setUpdatedAt(LocalDateTime.now());
    }

    private void addOutfitClothes(PatchedOutfitDto patchedOutfitDto, Outfit outfit) {

        if (patchedOutfitDto.getAddedClothes() != null && !patchedOutfitDto.getAddedClothes().isEmpty()) {

            List<OutfitClothes> outfitClothes = mapToOutfitClothesList(
                    patchedOutfitDto.getAddedClothes(),
                    outfit.getId()
            );

            outfitClothesRepository.saveAll(outfitClothes);
        }
    }

    private void patchOutfitClothes(PatchedOutfitDto patchedOutfitDto, List<OutfitClothes> outfitClothes) {

        if (patchedOutfitDto.getUpdatedClothes() != null && !patchedOutfitDto.getUpdatedClothes().isEmpty()) {

            patchedOutfitDto.getUpdatedClothes().forEach(item -> {

                Optional<OutfitClothes> optionalOutfitClothes = outfitClothes.stream()
                        .filter(oc -> oc.getId().equals(item.getId()))
                        .findFirst();

                if (optionalOutfitClothes.isPresent()) {
                    setPatchedOutfitClothingFields(item, optionalOutfitClothes.get());
                    outfitClothesRepository.save(optionalOutfitClothes.get());
                }

            });
        }
    }

    private static void setPatchedOutfitClothingFields(PatchedOutfitClothesDto patchedOutfitClothesDto,
                                                       OutfitClothes outfitClothes) {

        if (patchedOutfitClothesDto.getPositionX() != null) {
            outfitClothes.setPositionX(patchedOutfitClothesDto.getPositionX());
        }

        if (patchedOutfitClothesDto.getPositionY() != null) {
            outfitClothes.setPositionY(patchedOutfitClothesDto.getPositionY());
        }

        if (patchedOutfitClothesDto.getScale() != null) {
            outfitClothes.setScale(patchedOutfitClothesDto.getScale());
        }

        if (patchedOutfitClothesDto.getRotation() != null) {
            outfitClothes.setRotation(patchedOutfitClothesDto.getRotation());
        }

        if (patchedOutfitClothesDto.getZIndex() != null) {
            outfitClothes.setZIndex(patchedOutfitClothesDto.getZIndex());
        }

        outfitClothes.setAddedAt(LocalDateTime.now());
    }

    private void deleteOutfitClothes(PatchedOutfitDto patchedOutfitDto, List<OutfitClothes> outfitClothes) {

        if (patchedOutfitDto.getDeletedClothes() != null && !patchedOutfitDto.getDeletedClothes().isEmpty()) {

            Set<Long> outfitClothingIds = outfitClothes.stream()
                    .map(OutfitClothes::getId)
                    .collect(Collectors.toSet());

            patchedOutfitDto.getDeletedClothes().forEach(deletedItem -> {
                if (outfitClothingIds.contains(deletedItem.getId())) {
                    outfitClothesRepository.deleteById(deletedItem.getId());
                }
            });
        }
    }


    @Transactional
    public ResponseEntity<Void> deleteOutfitById(Long id, boolean force) {

        final String email = AuthUtils.getEmail();

        try {
            if (!force) {
                Optional<OutfitPlan> optionalOutfitPlan = outfitPlanRepository.findFirstByOutfitId(id);

                if (optionalOutfitPlan.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            }

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
