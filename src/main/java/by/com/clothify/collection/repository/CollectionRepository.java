package by.com.clothify.collection.repository;

import by.com.clothify.collection.model.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Optional<Collection> findByIdAndUser_Email(Long id, String email);

    void deleteByIdAndUser_Email(Long id, String email);
}
