package software.sigma.sip.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.sip.application.service.MoneyService;
import software.sigma.sip.infrastructure.dto.WalletDto;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets/money")
public class MoneyController {
    private final MoneyService moneyService;

    @PutMapping("/{currencyId}/{value}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDto adjunctionMoney(@PathVariable Long currencyId, @PathVariable String value) {
        return WalletDto.toWalletDto(moneyService.adjunctionMoney(currencyId, value));
    }

    @PutMapping("/{sourceCurrency}/{targetCurrency}/{value}")
    @ResponseStatus(HttpStatus.OK)
    public void transferMoney(@PathVariable Long sourceCurrency, @PathVariable Long targetCurrency, @PathVariable String value, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        moneyService.transferMoney(sourceCurrency, targetCurrency,value,username);
    }
}
