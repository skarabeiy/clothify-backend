package by.com.clothify.statistics.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicStatisticDto {

    private ItemsDto items;

    private ValueDto value;

    private UsageDto usage;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemsDto {

        @Schema(example = "100")
        private Integer totalCount;

        @Schema(example = "{\"Tops and T-shirts\": 20, \"Pants and jeans\": 30, \"Shoes\": 10, \"Outwear\": 30, \"Jackets\": 10}")
        private Map<String, Integer> additionalInfo;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueDto {

        @Schema(example = "10000.0")
        private Double totalCost;

        @Schema(example = "{\"Tops and T-shirts\": 2000, \"Pants and jeans\": 3000, \"Shoes\": 1000, \"Outwear\": 3000, \"Jackets\": 1000}")
        private Map<String, Integer> additionalInfo;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsageDto {

        @Schema(example = "100")
        private Integer totalCount;

        @Schema(example = "17")
        private Integer usageCount;
    }
}
