package by.com.clothify.collection.repository;

import by.com.clothify.collection.model.entity.CollectionClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionClothesRepository extends JpaRepository<CollectionClothes, Long> {
}
