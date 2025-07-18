package by.com.clothify.security.repository;

import by.com.clothify.security.model.entity.AppleClientSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppleClientSecretRepository extends JpaRepository<AppleClientSecret, Long> {

    Optional<AppleClientSecret> findFirstBy();
}
