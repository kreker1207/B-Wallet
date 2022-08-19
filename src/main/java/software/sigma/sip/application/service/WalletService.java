package software.sigma.sip.application.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.domain.repository.WalletRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class WalletService {
    private static final String ERROR_TEMPLATE = "Wallet was not found by id";
    private WalletRepository walletRepository;

    public Wallet getWallet(Long id) {
        log.info("Get Wallet: '{}'", id);
        return walletRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(ERROR_TEMPLATE);
        });
    }

    public void addWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public Wallet updateWallet(Wallet wallet, Long id) {
        if (!walletRepository.existsById(id)) {
            throw new EntityNotFoundException(ERROR_TEMPLATE);
        }
        return walletRepository.save(builderHelper(wallet, id));

    }

    public void deleteWallet(Long id) {
        if (!walletRepository.existsById(id)) {
            throw new EntityNotFoundException(ERROR_TEMPLATE);
        }
        walletRepository.deleteById(id);

    }

    private Wallet builderHelper(Wallet wallet, Long id) {
        Wallet oldWallet = getWallet(id);
        return Wallet.builder()
                .id(id)
                .ownerId(Objects.isNull(wallet.getOwnerId()) ? oldWallet.getOwnerId() : wallet.getOwnerId())
                .name(Objects.isNull(wallet.getName()) ? oldWallet.getName() : wallet.getName())
                .currency(Objects.isNull(wallet.getCurrency()) ? oldWallet.getCurrency() : wallet.getCurrency())
                .amount(Objects.isNull(wallet.getAmount()) ? oldWallet.getAmount() : wallet.getAmount())
                .createdAt(Objects.isNull(wallet.getCreatedAt()) ? oldWallet.getCreatedAt() : wallet.getCreatedAt())
                .build();

    }
}