package software.sigma.sip.application.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.domain.repository.WalletRepository;
import software.sigma.sip.infrastructure.dto.WalletDto;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class WalletService {
    private static final String ERROR_TEMPLATE = "Wallet Does Not Found by ID";
    private WalletRepository walletRepository;

    public WalletDto getWallet(Long id) {
        log.info("Get Wallet: '{}'", id);
        return WalletDto.toWalletDto(walletRepository.findById(id).orElseThrow(()->{throw new RuntimeException(ERROR_TEMPLATE);}));
    }

    public void addWallet(Wallet wallet){
        walletRepository.save(wallet);
    }
    public void updateWallet(Wallet wallet,Long id){
        if(!walletRepository.existsById(id)){
            throw new RuntimeException(ERROR_TEMPLATE);
        }
        Wallet oldWallet = walletRepository.findById(id).orElseThrow(()->{throw new RuntimeException(ERROR_TEMPLATE);});
        walletRepository.save(Wallet.builder()
                .id(id)
                .name(Objects.isNull(wallet.getName())?oldWallet.getName():wallet.getName())
                .currency(Objects.isNull(wallet.getCurrency())?oldWallet.getCurrency():wallet.getCurrency())
                .amount(Objects.isNull(wallet.getAmount())?oldWallet.getAmount():wallet.getAmount())
                .createdAt(Objects.isNull(wallet.getCreatedAt())?oldWallet.getCreatedAt():wallet.getCreatedAt())
                .build());

    }
    public void  deleteWallet(Long id){
        if(!walletRepository.existsById(id)){
            throw new RuntimeException(ERROR_TEMPLATE);
        }
        walletRepository.deleteById(id);

    }
}