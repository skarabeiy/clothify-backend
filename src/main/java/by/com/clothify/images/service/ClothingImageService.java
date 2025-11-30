package by.com.clothify.images.service;

import by.com.clothify.clothes.model.entity.Image;
import by.com.clothify.clothes.service.file.ImageService;
import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.images.mapper.ClothingImageMapper;
import by.com.clothify.images.model.dto.ClothingImageDto;
import by.com.clothify.images.model.dto.UploadedClothingImageDto;
import by.com.clothify.images.model.entity.ClothingImage;
import by.com.clothify.images.repository.ClothingImageRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static by.com.clothify.images.mapper.ClothingImageMapper.mapToClothingImageDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClothingImageService {

    private final ImageService imageService;
    private final ClothingImageRepository clothingImageRepository;
    private final UserRepository userRepository;

    public ResponseEntity<ClothingImageDto> getClothingImageById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<ClothingImage> optionalClothingImage = clothingImageRepository.findByIdAndEmail(id, email);

            if (optionalClothingImage.isEmpty()) {
                log.error("Can't find clothing image by id = {} for user: {}", id, email);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(mapToClothingImageDto(optionalClothingImage.get()));

        } catch (Exception e) {
            log.error("Failed get clothing image by id = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ClothingImageDto saveClothingImage(UploadedClothingImageDto uploadedImageDto) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            Image image = imageService.saveImage(uploadedImageDto.getImageFile());

            ClothingImage clothingImage = ClothingImageMapper.mapToClothingImage(uploadedImageDto);
            clothingImage.setUser(user);
            clothingImage.setImage(image);

            LocalDateTime now = LocalDateTime.now();
            clothingImage.setCreatedAt(now);
            clothingImage.setUpdatedAt(now);

            clothingImage = clothingImageRepository.save(clothingImage);

            return mapToClothingImageDto(clothingImage);

        } catch (Exception e) {
            log.error("Failed save clothing image: {} for user: {}", uploadedImageDto.getName(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ResponseEntity<Void> deleteClothingImageById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Image> optionalImage = imageService.getImageByClothingImageIdAndEmail(id, email);

            if (optionalImage.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            clothingImageRepository.deleteByIdAndUser_Email(id, email);

            imageService.deleteImageFromCloud(optionalImage.get().getPublicId());

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed delete clothing image with ID = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
