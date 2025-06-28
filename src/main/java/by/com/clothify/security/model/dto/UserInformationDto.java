package by.com.clothify.security.model.dto;

import by.com.clothify.security.model.dto.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationDto {

    private String name;

    private Gender gender;

    private String dateOfBirth;
}
