package software.sigma.sip.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.domain.repository.WalletRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class WalletServiceTest {

    private static WalletRepository walletRepository;
    private static WalletService walletService;

    @BeforeAll
    public static void setup() {
        walletRepository = Mockito.mock(WalletRepository.class);
        walletService = new WalletService(walletRepository);
    }

    @Test
    void addWallet_success() {
        Wallet sourceWallet = new Wallet(1L, 1L, "Ivan", "USD", "200.00", "17.08");

        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(sourceWallet));
        walletService.addWallet(sourceWallet);

        Mockito.verify(walletRepository).save(sourceWallet);
    }

    @Test
    void getWallet_success() {
        Long id = 1L;
        Wallet source = new Wallet(1L, 1L, "Ivan", "USD", "200.00", "17.08");
        Mockito.when(walletRepository.findById(1L))
                .thenReturn(Optional.of(source));
        Wallet actualWallet = walletService.getWallet(id);
        assertThat(actualWallet).isEqualTo(source);
    }

    @Test
    void getWallet_failure() {
        Long id = 1L;

        Mockito.when(walletRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> walletService.getWallet(id));
    }

    @Test
    void deleteWallet_success() {
        Long id = 1L;
        Mockito.when(walletRepository.existsById(id)).thenReturn(true);
        walletService.deleteWallet(id);

        Mockito.verify(walletRepository).deleteById(id);
    }

    @Test
    void deleteWallet_failure() {
        Long id = 1L;
        Mockito.when(walletRepository.existsById(id)).thenReturn(false);
        String expectedMessage = "Wallet was not found by id";
        String actualMessage = Assertions.assertThrows(RuntimeException.class, () ->
                walletService.deleteWallet(id)).getMessage();
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    void updateWallet_success() {
        Wallet sourceWallet = new Wallet(1L, 2L, "Ivan", "USD", "200.00", "17.08");
        Wallet expected = new Wallet(1L, 2L, "Ivan", "USD", "200.00", "17.08");
        Long id = 1L;

        Mockito.when(walletRepository.existsById(1L))
                .thenReturn(true);
        Mockito.when(walletRepository.findById(1L))
                .thenReturn(Optional.of(sourceWallet));

        walletService.updateWallet(sourceWallet, id);

        assertThat(walletService.updateWallet(sourceWallet, id)).isEqualTo(sourceWallet);

    }

    @Test
    void updateWallet_failure() {
        Wallet sourceWallet = new Wallet(1L, 2L, "Ivan", "USD", "200.00", "17.08");
        Long id = 1L;

        Mockito.when(walletRepository.existsById(Mockito.eq(id))).thenReturn(false);
        String expectedMessage = "Wallet was not found by id";

        String actualMessage = Assertions.assertThrows(RuntimeException.class, () ->
                walletService.updateWallet(sourceWallet, id)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}
