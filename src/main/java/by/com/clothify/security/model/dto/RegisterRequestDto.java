package by.com.clothify.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    private String email;

    private String password;

    private String passwordConfirmation;

    private String name;

    private Gender gender;

    private String dateOfBirth;

    public enum Gender {
        FEMALE,
        MALE
    }
}