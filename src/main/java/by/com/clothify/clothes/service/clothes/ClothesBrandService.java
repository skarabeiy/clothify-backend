package by.com.clothify.clothes.service.clothes;

import by.com.clothify.clothes.mapper.ClothesBrandMapper;
import by.com.clothify.clothes.model.dto.ClothesBrandDto;
import by.com.clothify.clothes.model.dto.ClothesBrandRequestDto;
import by.com.clothify.clothes.model.entity.ClothesBrand;
import by.com.clothify.clothes.repository.ClothesBrandRepository;
import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClothesBrandService {

    private final UserRepository userRepository;
    private final ClothesBrandRepository clothesBrandRepository;

    public ClothesBrandDto saveCustomClothesBrand(ClothesBrandRequestDto request) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();

            ClothesBrand clothesBrand = ClothesBrand.builder()
                    .name(request.getName())
                    .isCustom(true)
                    .user(user)
                    .build();

            clothesBrand = clothesBrandRepository.save(clothesBrand);

            return ClothesBrandMapper.mapToClothesBrandDto(clothesBrand);

        } catch (Exception e) {
            log.error("Failed save custom brand: {} for user: {}", request.getName(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
