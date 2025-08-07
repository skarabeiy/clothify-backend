package by.com.clothify.profile.model.dto;

import by.com.clothify.profile.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilePatchRequestDto {

    @Schema(example = "Tom")
    @Nullable
    private String name;

    @Schema(example = "null")
    @Nullable
    private Gender gender;

    @Schema(example = "null")
    @Nullable
    private String dateOfBirth;
}
