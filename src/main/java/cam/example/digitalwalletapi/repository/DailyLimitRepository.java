package cam.example.digitalwalletapi.repository;

import cam.example.digitalwalletapi.entity.DailyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyLimitRepository extends JpaRepository<DailyLimit, Long> {
    Optional<DailyLimit> findByUserId(Long userId);
}

