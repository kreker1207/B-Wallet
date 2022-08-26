package software.sigma.sip.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.sip.application.service.WalletService;
import software.sigma.sip.infrastructure.dto.WalletDto;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWallet(@RequestBody WalletDto walletDto) {
        walletService.addWallet(walletDto.toWallet());
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDto getWallet(@PathVariable Long id) {
        return WalletDto.toWalletDto(walletService.getWallet(id));
    }

    @DeleteMapping("user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWallet(@PathVariable Long id) {
        walletService.deleteWallet(id);
    }

    @PutMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDto updateWallet(@RequestBody WalletDto walletDto, @PathVariable Long id) {
        return WalletDto.toWalletDto(walletService.updateWallet(walletDto.toWallet(), id));
    }
}