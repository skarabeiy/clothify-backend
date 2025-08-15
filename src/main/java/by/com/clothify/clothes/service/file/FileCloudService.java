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

    private final Cloudinary cloudinary;

    @SuppressWarnings("unchecked")
    public UploadFileResponseDto uploadFileToCloud(MultipartFile file) {

        try {
            Map<String, String> options = Map.of(
                    "folder", "clothify"
            );

            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                    file.getBytes(),
                    options
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
}
