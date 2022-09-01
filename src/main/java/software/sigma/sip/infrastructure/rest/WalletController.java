package software.sigma.sip.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.sip.application.service.WalletService;
import software.sigma.sip.infrastructure.dto.WalletDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addWallet(@RequestBody WalletDto walletDto) {
        walletService.addWallet(walletDto.toWallet());
    }

    @GetMapping
    @Secured("ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public List<WalletDto> getWallets() {
        return walletService.getWallets().stream().map(WalletDto::toWalletDto).toList();
    }

    @GetMapping(params = "ownerId")
    @ResponseStatus(HttpStatus.OK)
    public List<WalletDto> getWalletsByOwnerId(@RequestParam Long ownerId) {
        return walletService.getWalletsByOwnerId(ownerId).stream().map(WalletDto::toWalletDto).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDto getWallet(@PathVariable Long id) {
        return WalletDto.toWalletDto(walletService.getWallet(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWallet(@PathVariable Long id) {
        walletService.deleteWallet(id);
    }

    @PutMapping("/{id}/{value}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDto adjunctionMoney(@PathVariable Long id, @PathVariable String value) {
        return WalletDto.toWalletDto(walletService.adjunctionMoney(id, value));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDto updateWallet(@RequestBody WalletDto walletDto, @PathVariable Long id) {
        return WalletDto.toWalletDto(walletService.updateWallet(walletDto.toWallet(), id));
    }
}