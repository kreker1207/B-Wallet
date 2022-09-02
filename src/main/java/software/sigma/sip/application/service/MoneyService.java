package software.sigma.sip.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.sigma.sip.client.exception.UserForbiddenException;
import software.sigma.sip.domain.entity.User;
import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.domain.repository.UserRepository;
import software.sigma.sip.domain.repository.WalletRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyService {

    private static final String ERROR_TEMPLATE_WALLET = "Wallet was not found by id";
    private static final String ERROR_TEMPLATE_USER = "User was not found by id";
    private static final String ERROR_TEMPLATE = "Could not use this wallet";

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final CurrencyService currencyService;

    @Transactional
    public Wallet adjunctionMoney(Long id, String value) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ERROR_TEMPLATE_WALLET));
        wallet.setAmount(new BigDecimal(wallet.getAmount()).add(new BigDecimal(value)).toString());
        return walletRepository.save(wallet);
    }

    @Transactional
    public void transferMoney(Long sourceCurrency, Long targetCurrency, String value, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(ERROR_TEMPLATE_USER));
        Wallet sourceWallet = walletRepository.findById(sourceCurrency).orElseThrow (() ->
                new EntityNotFoundException(ERROR_TEMPLATE_WALLET));
        if(!user.getId().equals(sourceWallet.getOwnerId())) {
            throw new UserForbiddenException(ERROR_TEMPLATE);
        }
        Wallet targetWallet = walletRepository.findById(targetCurrency).orElseThrow(() -> new EntityNotFoundException(ERROR_TEMPLATE_WALLET));
        sourceWallet.setAmount(new BigDecimal(sourceWallet.getAmount()).subtract(new BigDecimal(value)).toString());
        Map<String, String> map = currencyService.getValue(sourceWallet.getCurrency(), List.of(targetWallet.getCurrency()));
        targetWallet.setAmount (
                new BigDecimal(targetWallet.getAmount()).add (new BigDecimal(value)).multiply(new BigDecimal(map.get(targetWallet.getCurrency()))).toString ()
        );
        walletRepository.save(sourceWallet);
        walletRepository.save(targetWallet);
    }

}
