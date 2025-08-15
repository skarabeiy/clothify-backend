package by.com.clothify.clothes.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final Cloudinary cloudinary;

    public String uploadFileToCloud(MultipartFile file) {

        try {
            Map<String, String> options = Map.of(
                    "folder", "clothify"
            );

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    options
            );
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
