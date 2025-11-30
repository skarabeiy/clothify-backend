package by.com.clothify.images.model.entity;

import by.com.clothify.clothes.model.entity.ClothesBrand;
import by.com.clothify.clothes.model.entity.ClothesCategory;
import by.com.clothify.clothes.model.entity.Image;
import by.com.clothify.common.model.enums.Season;
import by.com.clothify.security.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "clothing_images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClothingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false)
    private Image image;

    @Column(length = 30)
    private String backgroundColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_category_id", nullable = false)
    private ClothesCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_brand_id", nullable = false)
    private ClothesBrand brand;

    @Column(nullable = false, length = 30)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Season season;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
