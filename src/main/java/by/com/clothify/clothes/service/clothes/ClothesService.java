package by.com.clothify.clothes.service.clothes;

import by.com.clothify.clothes.mapper.ClothesMapper;
import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.model.dto.ClothesFilterDto;
import by.com.clothify.clothes.model.dto.PatchedClothesDto;
import by.com.clothify.clothes.model.dto.UploadedClothesDto;
import by.com.clothify.clothes.model.entity.Clothes;
import by.com.clothify.clothes.model.entity.ClothesBrand;
import by.com.clothify.clothes.model.entity.ClothesCategory;
import by.com.clothify.clothes.model.entity.Image;
import by.com.clothify.clothes.repository.ClothesRepository;
import by.com.clothify.clothes.service.file.ImageService;
import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.common.util.DateUtils;
import by.com.clothify.outfit.model.entity.Outfit;
import by.com.clothify.outfit.repository.OutfitRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static by.com.clothify.clothes.mapper.ClothesMapper.mapToClothesDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClothesService {

    private final ImageService imageService;
    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;
    private final OutfitRepository outfitRepository;

    public ResponseEntity<ClothesDto> getClothesById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Clothes> optionalClothes = clothesRepository.findByIdAndEmail(id, email);

            if (optionalClothes.isEmpty()) {
                log.error("Can't find clothes by id = {} for user: {}", id, email);
                return ResponseEntity.notFound().build();
            }

            List<Long> outfitIds = outfitRepository.findAllByClothingId(optionalClothes.get().getId());
            List<Outfit> outfits = outfitRepository.findOutfitsByIds(outfitIds);

            return ResponseEntity.ok(mapToClothesDto(optionalClothes.get()));

        } catch (Exception e) {
            log.error("Failed get clothes by id = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Page<ClothesDto> getFilteredClothes(ClothesFilterDto filter, int page, int size) {

        final String email = AuthUtils.getEmail();

        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Long> clothesIds = clothesRepository.findFilteredClothesIds(email, pageable, filter);

            if (clothesIds.isEmpty()) {
                return Page.empty(pageable);
            }

            List<ClothesDto> clothes = clothesRepository.findClothesByIds(clothesIds.getContent()).stream()
                    .map(ClothesMapper::mapToPageClothesDto)
                    .toList();

            return new PageImpl<>(clothes, pageable, clothesIds.getTotalElements());

        } catch (Exception e) {
            log.error("Failed get clothes by category id = {}, for user: {}", filter.getCategoryId(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ClothesDto saveClothes(UploadedClothesDto uploadedClothes) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            Image image = imageService.saveImage(uploadedClothes.getImageFile());

            Clothes clothes = ClothesMapper.mapToClothes(uploadedClothes);
            clothes.setUser(user);
            clothes.setImage(image);

            LocalDateTime now = LocalDateTime.now();
            clothes.setCreatedAt(now);
            clothes.setUpdatedAt(now);

            clothes = clothesRepository.save(clothes);

            return mapToClothesDto(clothes);

        } catch (Exception e) {
            log.error("Failed save clothes: {} for user: {}", uploadedClothes.getName(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ResponseEntity<ClothesDto> patchClothes(PatchedClothesDto patchedClothes) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Clothes> optionalClothes = clothesRepository.findByIdAndEmail(patchedClothes.getId(), email);

            if (optionalClothes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Clothes clothes = optionalClothes.get();

            if (patchedClothes.getImageFile() != null) {
                Image image = imageService.updateImage(clothes.getImage(), patchedClothes.getImageFile());
                clothes.setImage(image);
            }

            setPatchedClothesFields(clothes, patchedClothes);

            clothes.setUpdatedAt(LocalDateTime.now());

            clothes = clothesRepository.save(clothes);

            return ResponseEntity.ok(mapToClothesDto(clothes));

        } catch (Exception e) {
            log.error("Failed patch clothes with ID = {} for user: {}", patchedClothes.getId(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static void setPatchedClothesFields(Clothes clothes, PatchedClothesDto patchedClothes) {

        if (patchedClothes.getName() != null) {
            clothes.setName(patchedClothes.getName());
        }

        if (patchedClothes.getBackgroundColor() != null) {
            clothes.setBackgroundColor(patchedClothes.getBackgroundColor());
        }

        if (patchedClothes.getCategoryId() != null) {
            clothes.setCategory(
                    ClothesCategory.builder()
                            .id(patchedClothes.getCategoryId())
                            .build()
            );
        }

        if (patchedClothes.getBrandId() != null) {
            clothes.setBrand(
                    ClothesBrand.builder()
                            .id(patchedClothes.getBrandId())
                            .build()
            );
        }

        if (patchedClothes.getColor() != null) {
            clothes.setColor(patchedClothes.getColor());
        }

        if (patchedClothes.getSeason() != null) {
            clothes.setSeason(patchedClothes.getSeason());
        }

        if (patchedClothes.getPrice() != null) {
            clothes.setPrice(patchedClothes.getPrice());
        }

        if (patchedClothes.getDateOfPurchase() != null) {
            clothes.setDateOfPurchase(DateUtils.covertToLocaleDate(patchedClothes.getDateOfPurchase()));
        }
    }

    @Transactional
    public ResponseEntity<Void> deleteClothesById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Image> optionalImage = imageService.getImageByClothesIdAndEmail(id, email);

            if (optionalImage.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            clothesRepository.deleteByIdAndUser_Email(id, email);

            imageService.deleteImageFromCloud(optionalImage.get().getPublicId());

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed delete clothes with ID = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<String> getClothesNames() {

        final String email = AuthUtils.getEmail();

        try {
            return clothesRepository.findClothesNames(email).stream()
                    .distinct()
                    .toList();

        } catch (Exception e) {
            log.error("Failed get clothes names for user: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
