package by.com.clothify.links.repository;

import by.com.clothify.links.model.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("""
            SELECT l FROM Link l
            JOIN l.user u
            JOIN FETCH l.category
            JOIN FETCH l.brand
            WHERE l.id = :id
            AND u.email = :email
            """)
    Optional<Link> findByIdAndEmail(Long id, String email);
}
