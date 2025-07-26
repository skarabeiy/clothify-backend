package by.com.clothify.security.repository;

import by.com.clothify.security.model.entity.SendCodeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendCodeRequestRepository extends JpaRepository<SendCodeRequest, Long> {
}
