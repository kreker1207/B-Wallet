package software.sigma.sip.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Money", description = "Replenishment wallet and money transfer")
public class MoneyController {
    private final MoneyService moneyService;

    @PutMapping("/{currencyId}/{value}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Adjunct Money", responses = {
            @ApiResponse(responseCode = "201", description = "Wallet was replenished"),
            @ApiResponse(responseCode = "404", description = "Wallet was not found by id"),
            @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
    })
    public WalletDto adjunctionMoney(@PathVariable Long currencyId, @PathVariable String value) {
        return WalletDto.toWalletDto(moneyService.adjunctionMoney(currencyId, value));
    }

    @PutMapping("/{sourceWalletId}/{targetWalletId}/{value}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Transfer Money", responses = {
            @ApiResponse(responseCode = "201", description = "Money were transferred from one wallet to another"),
            @ApiResponse(responseCode = "404", description = "Wallet was not found by id"),
            @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
    })
    public void transferMoney(@PathVariable Long sourceWalletId, @PathVariable Long targetWalletId, @PathVariable String value, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        moneyService.transferMoney(sourceWalletId, targetWalletId,value,username);
    }
}
