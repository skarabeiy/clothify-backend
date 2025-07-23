package by.com.clothify.security.model.dto.apple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppleResponseDto {

    private boolean success;

    @Nullable
    private String email;

    public Optional<String> getEmail() {

        return email != null
                ? Optional.of(email)
                : Optional.empty();
    }
}
