package by.com.clothify.clothes.service.clothes;

import by.com.clothify.clothes.mapper.ClothesMapper;
import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.repository.ClothesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClothesStatisticService {

    private final ClothesRepository clothesRepository;

    public List<ClothesDto> getAllClothesByEmail(String email) {

        return clothesRepository.findAllByUser_Email(email).stream()
                .map(ClothesMapper::mapToClothesDto)
                .toList();
    }
}
