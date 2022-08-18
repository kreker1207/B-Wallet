package software.sigma.sip.infrastructure.dto;

import lombok.Builder;
import lombok.Data;
import software.sigma.sip.domain.entity.Wallet;


@Data
@Builder
public class WalletDto {
    Long id;
    Long ownerId;
    String name;
    String currency;
    String amount;
    String createdAt;

    public static WalletDto toWalletDto(Wallet wallet) {
        return WalletDto.builder()
                .id(wallet.getId())
                .ownerId(wallet.getOwnerId())
                .name(wallet.getName())
                .currency(wallet.getCurrency())
                .amount(wallet.getAmount())
                .createdAt(wallet.getCreatedAt())
                .build();
    }
}
