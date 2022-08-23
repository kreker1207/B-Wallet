package software.sigma.sip.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.sip.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
