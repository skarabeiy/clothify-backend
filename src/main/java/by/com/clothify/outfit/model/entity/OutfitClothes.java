package by.com.clothify.outfit.model.entity;

import by.com.clothify.clothes.model.entity.Clothes;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "outfit_clothes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OutfitClothes {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private Id id;

    @ManyToOne
    @MapsId("outfitId")
    @JoinColumn(name = "outfit_id", nullable = false)
    private Outfit outfit;

    @ManyToOne
    @MapsId("clothesId")
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes;

    @Column(nullable = false)
    private Double positionX;

    @Column(nullable = false)
    private Double positionY;

    private Double scale;

    private Double rotation;

    private Integer zIndex;

    @Column(nullable = false)
    private LocalDateTime addedAt;

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {

        private Long outfitId;

        private Long clothesId;
    }
}
