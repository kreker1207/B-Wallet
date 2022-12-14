package software.sigma.sip.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.domain.repository.WalletRepository;
import software.sigma.sip.exception.EntityNotFoundByIdException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {
    private static final String ERROR_TEMPLATE = "Wallet was not found by id";
    private final WalletRepository walletRepository;

    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }

    public Wallet getWallet(Long id) {
        log.info("Get Wallet: '{}'", id);
        return walletRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        });
    }

    public List<Wallet> getWalletsByOwnerId(Long ownerId) {
        return walletRepository.findWalletsByOwnerId(ownerId);
    }

    public void addWallet(Wallet wallet) {
        if (wallet.getCreatedAt() == null) {
            wallet.setCreatedAt(LocalDate.now().toString());
        }
        walletRepository.save(wallet);
    }

    public Wallet updateWallet(Wallet wallet, Long id) {
        if (!walletRepository.existsById(id)) {
            throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        }
        Wallet oldWallet = getWallet(id);
        return walletRepository.save(applyChanges(wallet, oldWallet, id));

    }

    public void deleteWallet(Long id) {
        if (!walletRepository.existsById(id)) {
            throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        }
        walletRepository.deleteById(id);

    }

    private Wallet applyChanges(Wallet newWallet, Wallet oldWallet, Long id) {
        return Wallet.builder()
                .id(id)
                .ownerId(Objects.isNull(newWallet.getOwnerId()) ? oldWallet.getOwnerId() : newWallet.getOwnerId())
                .name(Objects.isNull(newWallet.getName()) ? oldWallet.getName() : newWallet.getName())
                .currency(Objects.isNull(newWallet.getCurrency()) ? oldWallet.getCurrency() : newWallet.getCurrency())
                .amount(Objects.isNull(newWallet.getAmount()) ? oldWallet.getAmount() : newWallet.getAmount())
                .createdAt(Objects.isNull(newWallet.getCreatedAt()) ? oldWallet.getCreatedAt() : newWallet.getCreatedAt())
                .convertedCurrency(Objects.isNull(newWallet.getConvertedCurrency()) ? oldWallet.getConvertedCurrency() : newWallet.getConvertedCurrency())
                .build();
    }
}