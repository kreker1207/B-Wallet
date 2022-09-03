package software.sigma.sip.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Wallets", description = "CRUD operations for wallet resources")
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create wallet",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Wallet was created"),
                    @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
            }
    )
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
    @Operation(
            summary = "Get all wallets or wallets by ownerId",
            parameters = {
                    @Parameter(name = "ownerId", description = "Id of wallets owner")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found wallets", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = WalletDto.class)))}),
                    @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
            }
    )
    public List<WalletDto> getWalletsByOwnerId(@RequestParam(required = false) Long ownerId) {
        return walletService.getWalletsByOwnerId(ownerId).stream().map(WalletDto::toWalletDto).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get wallet by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found wallet by id", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = WalletDto.class))}),
                    @ApiResponse(responseCode = "404", description = "Wallet was not found by id", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
            }
    )
    public WalletDto getWallet(@PathVariable Long id) {
        return WalletDto.toWalletDto(walletService.getWallet(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete wallet by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Wallet was deleted"),
                    @ApiResponse(responseCode = "404", description = "Wallet was not found by id"),
                    @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
            }
    )
    public void deleteWallet(@PathVariable Long id) {
        walletService.deleteWallet(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update wallet by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Wallet was updated"),
                    @ApiResponse(responseCode = "404", description = "Wallet was not found by id", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
            }
    )
    public WalletDto updateWallet(@RequestBody WalletDto walletDto, @PathVariable Long id) {
        return WalletDto.toWalletDto(walletService.updateWallet(walletDto.toWallet(), id));
    }
}