package by.com.clothify.statistics.service;

import by.com.clothify.statistics.model.dto.BasicStatisticDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    public BasicStatisticDto getBasicStatistic() {

        return null;
    }

    public void test() {

        // 1. items - нужно посчитать общее кол-во одежды у пользователя

        // 2. items дополнительно - получить запросом одежду пользователя (интересует только тип)
        // и посчитать по типам.

        // 3. Value - посчитать общую стоимость одежды у пользователя

        // 4. value дополнительно - получить всю одежду, стоимость посчитать по типам

        // 5. usage - получить общее кол-во одежды + получить кол-во одежды, которая участвует в аутфитах,
        // посчитать
    }
}
