package software.sigma.sip.infrastructure.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.infrastructure.dto.WalletDto;


@RestController
@AllArgsConstructor
@RequestMapping("/wallets")
public class WalletController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addWallet(@RequestBody Wallet wallet){

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDto getWallet(@PathVariable Long id){
    return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWallet(@PathVariable Long id){

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateWallet(@RequestBody Wallet wallet,@PathVariable Long id){

    }
}