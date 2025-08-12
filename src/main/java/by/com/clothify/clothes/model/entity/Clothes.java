package by.com.clothify.clothes.model.entity;

import by.com.clothify.clothes.model.enums.Season;
import by.com.clothify.security.model.entity.User;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clothes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "auth_user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String image;

    @Column(length = 30)
    private String backgroundColor;

    @ManyToOne
    @JoinColumn(name = "clothes_category_id", nullable = false)
    private ClothesCategory category;

    @ManyToOne
    @JoinColumn(name = "common_clothes_brand_id")
    private CommonClothesBrand commonBrand;

    @ManyToOne
    @JoinColumn(name = "custom_clothes_brand_id")
    private CustomClothesBrand customBrand;

    @Column(nullable = false, length = 30)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Season season;

    private String price;

    private LocalDate dateOfPurchase;
}
