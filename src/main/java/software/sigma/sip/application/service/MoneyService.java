package software.sigma.sip.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public Wallet adjunctionMoney(Long id, String value) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ERROR_TEMPLATE_WALLET));
        wallet.setAmount(new BigDecimal(wallet.getAmount()).add(new BigDecimal(value)).toString());
        return walletRepository.save(wallet);
    }
    public void transferMoney(Long src, Long tg, String value, String username){
        User user = userRepository.findByUsername(username).orElseThrow(()->
                new EntityNotFoundException(ERROR_TEMPLATE_USER));
        Wallet srcWallet = walletRepository.findById(src).orElseThrow(()->
                new EntityNotFoundException(ERROR_TEMPLATE_WALLET));
        if(!user.getId().equals(srcWallet.getOwnerId())){throw new UserForbiddenException(ERROR_TEMPLATE);}
        Wallet tgWallet = walletRepository.findById(tg).orElseThrow(()->
                new EntityNotFoundException(ERROR_TEMPLATE_WALLET));
        srcWallet.setAmount(new BigDecimal(srcWallet.getAmount()).subtract(new BigDecimal(value)).toString());
        Map<String,String> map = currencyService.getValue(srcWallet.getCurrency(), List.of(tgWallet.getCurrency()));
        tgWallet.setAmount(new BigDecimal(tgWallet.getAmount()).add(new BigDecimal(value)).multiply(new BigDecimal(map.get(tgWallet.getCurrency()))).toString());
        walletRepository.save(srcWallet);
        walletRepository.save(tgWallet);
    }

}
