package software.sigma.sip.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.sip.domain.entity.Wallet;

@Repository
public interface WalletRepository  extends JpaRepository<Wallet, Long> {
}
