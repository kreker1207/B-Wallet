package software.sigma.sip.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.sigma.sip.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
