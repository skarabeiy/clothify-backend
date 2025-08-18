package by.com.clothify.clothes.service.file;

import by.com.clothify.clothes.model.dto.UploadFileResponseDto;
import by.com.clothify.clothes.model.entity.Image;
import by.com.clothify.clothes.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final FileCloudService fileCloudService;
    private final ImageRepository imageRepository;

    public Image saveImage(MultipartFile file) {

        try {
            UploadFileResponseDto uploadFileResponse = fileCloudService.uploadFileToCloud(file);

            Image image = Image.builder()
                    .publicId(uploadFileResponse.getPublicId())
                    .url(uploadFileResponse.getFileUrl())
                    .build();

            return imageRepository.save(image);

        } catch (Exception e) {
            log.error("Failed save file: {}", file);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Image> getImageByClothesIdAndEmail(Long clothesId, String email) {

        return imageRepository.findByClothesIdAndEmail(clothesId, email);
    }
}
