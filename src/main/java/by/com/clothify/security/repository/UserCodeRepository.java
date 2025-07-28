package by.com.clothify.security.repository;

import by.com.clothify.security.model.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCodeRepository extends JpaRepository<UserCode, Long> {

    Optional<UserCode> findByEmail(String email);
}
