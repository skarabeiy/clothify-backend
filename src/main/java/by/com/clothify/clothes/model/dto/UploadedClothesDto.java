package by.com.clothify.clothes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadedClothesDto {

    private String name;

    private MultipartFile imageFile;

    private String backgroundColor;

    private Long categoryId;
}
