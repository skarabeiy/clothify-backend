package by.com.clothify.links.repository;

import by.com.clothify.links.model.dto.LinkFilterDto;
import by.com.clothify.links.model.entity.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("""
            SELECT l FROM Link l
            JOIN l.user u
            LEFT JOIN FETCH l.category
            LEFT JOIN FETCH l.brand
            WHERE l.id = :id
            AND u.email = :email
            """)
    Optional<Link> findByIdAndEmail(Long id, String email);

    @Query("""
            SELECT l.id FROM Link l
            WHERE l.user.email = :email
            AND (:#{#filter.categoryId} IS NULL OR l.category.id = :#{#filter.categoryId})
            AND (COALESCE(:#{#filter.colors}, NULL) IS NULL OR l.color IN :#{#filter.colors})
            AND (COALESCE(:#{#filter.brandIds}, NULL) IS NULL OR l.brand.id IN :#{#filter.brandIds})
            AND (:#{#filter.season} IS NULL OR l.season = :#{#filter.season})
            AND (COALESCE(:#{#filter.names}, NULL) IS NULL OR l.name IN :#{#filter.names})
            ORDER BY l.updatedAt DESC
            """)
    Page<Long> findFilteredLinkIds(String email, Pageable pageable, LinkFilterDto filter);

    @Query("""
            SELECT l FROM Link l
            WHERE l.id IN :ids
            ORDER BY l.updatedAt DESC
            """)
    List<Link> findLinksByIds(List<Long> ids);

    void deleteByIdAndUser_Email(Long id, String email);

    @Query("""
            SELECT l.name FROM Link l
            WHERE l.user.email = :email
            ORDER BY l.updatedAt DESC
            """)
    List<String> findLinksNames(String email);
}
