package by.com.clothify.links.model.entity;

import by.com.clothify.clothes.model.entity.ClothesBrand;
import by.com.clothify.clothes.model.entity.ClothesCategory;
import by.com.clothify.common.model.enums.Season;
import by.com.clothify.security.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "links")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_category_id")
    private ClothesCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_brand_id")
    private ClothesBrand brand;

    @Column(length = 30)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Season season;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
