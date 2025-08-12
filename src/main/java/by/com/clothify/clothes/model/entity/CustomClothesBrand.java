package by.com.clothify.clothes.model.entity;

import by.com.clothify.security.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "custom_clothes_brands")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomClothesBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "auth_user_id", nullable = false)
    private User user;
}
