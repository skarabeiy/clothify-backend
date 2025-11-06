package by.com.clothify.statistics.model.dto;

import by.com.clothify.clothes.model.dto.ClothesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvancedStatisticDto {

    private List<ClothesDto> mostWornItems;

    private List<ClothesDto> leastWornItems;
}
