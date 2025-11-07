package by.com.clothify.clothes.service.clothes;

import by.com.clothify.clothes.mapper.ClothesMapper;
import by.com.clothify.clothes.model.dto.ClothesDto;
import by.com.clothify.clothes.repository.ClothesRepository;
import by.com.clothify.outfit.mapper.OutfitClothesMapper;
import by.com.clothify.outfit.model.dto.OutfitClothesDto;
import by.com.clothify.outfit.repository.OutfitClothesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClothesStatisticService {

    private final ClothesRepository clothesRepository;
    private final OutfitClothesRepository outfitClothesRepository;

    public List<ClothesDto> getClothesByEmailForBasic(String email) {

        return clothesRepository.findAllByUser_Email(email).stream()
                .map(ClothesMapper::mapToBasicStatisticClothesDto)
                .toList();
    }

    public List<ClothesDto> getClothesByEmailForAdvanced(String email) {

        return clothesRepository.findAllByEmailForAdvancedStatistic(email).stream()
                .map(ClothesMapper::mapToAdnvancedStatisticClothesDto)
                .toList();
    }

    public List<OutfitClothesDto> getOutfitClothesByEmailForBasic(String email) {

        return outfitClothesRepository.findAllByEmail(email).stream()
                .map(OutfitClothesMapper::mapToBasicStatisticOutfitClothesDto)
                .toList();
    }

    public List<OutfitClothesDto> getOutfitClothesByEmailForAdvanced(String email) {

        return outfitClothesRepository.findAllByEmailWithSorting(email).stream()
                .map(OutfitClothesMapper::mapToAdnvancedStatisticOutfitClothesDto)
                .toList();
    }
}
