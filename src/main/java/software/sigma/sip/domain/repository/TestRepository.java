package software.sigma.sip.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.sigma.sip.domain.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
}
