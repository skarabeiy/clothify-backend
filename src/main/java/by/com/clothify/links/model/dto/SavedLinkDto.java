package by.com.clothify.links.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedLinkDto {

    @Schema(example = "Name of link")
    private String name;

    @Schema(example = "https://www.adidas.com/us/firebird-track-pants/KD8315.html")
    private String url;
}
