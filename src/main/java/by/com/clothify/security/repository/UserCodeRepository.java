package by.com.clothify.security.repository;

import by.com.clothify.security.model.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserCodeRepository extends JpaRepository<UserCode, Long> {

    Optional<UserCode> findByEmail(String email);

    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime expirationTimeInSeconds);
}
