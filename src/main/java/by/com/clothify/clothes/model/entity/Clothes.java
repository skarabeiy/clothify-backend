package by.com.clothify.clothes.model.entity;

import by.com.clothify.clothes.model.enums.Season;
import by.com.clothify.security.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
