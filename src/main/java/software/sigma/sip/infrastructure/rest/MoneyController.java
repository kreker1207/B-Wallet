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

    @PutMapping("/{id}/{value}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDto adjunctionMoney(@PathVariable Long id, @PathVariable String value) {
        return WalletDto.toWalletDto(moneyService.adjunctionMoney(id, value));
    }

    @PutMapping("/{src}/{tg}/{value}")
    @ResponseStatus(HttpStatus.OK)
    public void transferMoney(@PathVariable Long src, @PathVariable Long tg, @PathVariable String value, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        moneyService.transferMoney(src,tg,value,username);
    }
}
