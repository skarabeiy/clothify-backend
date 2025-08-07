package by.com.clothify.profile.model.dto;

import by.com.clothify.profile.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequestDto {

    @Schema(example = "Bob")
    private String name;

    @Schema(example = "MALE")
    private Gender gender;

    @Schema(example = "06.06.1998")
    private String dateOfBirth;
}
