package by.com.clothify.clothes.service.clothes;

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

    public List<Object> getAllClothesByEmail(String email) {

        var res = clothesRepository.findAllByUser_Email(email);

        return null;
    }
}
