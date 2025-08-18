package by.com.clothify.clothes.service.file;

import by.com.clothify.clothes.model.dto.UploadFileResponseDto;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileCloudService {

    private static final Map<String, Object> UPLOAD_OPTIONS = Map.of(
            "folder", "clothify"
    );
    private static final Map<String, Object> DELETE_OPTIONS = Map.of(
            "invalidate", true
    );

    private final Cloudinary cloudinary;

    @SuppressWarnings("unchecked")
    public UploadFileResponseDto uploadFileToCloud(MultipartFile file) {

        try {

            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                    file.getBytes(),
                    UPLOAD_OPTIONS
            );
            return UploadFileResponseDto.builder()
                    .publicId(uploadResult.get("public_id").toString())
                    .fileUrl(uploadResult.get("secure_url").toString())
                    .build();

        } catch (Exception e) {
            log.error("Failed upload file: {} to cloud", file.getName());
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void deleteFileFromCloud(String publicId) {

        try {
            cloudinary.uploader().destroy(publicId, DELETE_OPTIONS);

        } catch (Exception e) {
            log.error("Failed delete image from cloud with publicId: {}", publicId);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
