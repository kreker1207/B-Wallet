package software.sigma.sip.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.domain.repository.WalletRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WalletServiceTest {
    private static WalletRepository walletRepository;
    private static WalletService walletService;

    @BeforeAll
    public static void setup(){
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
        Wallet expectedWallet = new Wallet(1L, 1L, "Ivan", "USD", "200.00", "17.08");
        Mockito.when(walletRepository.findById(1L))
                .thenReturn(Optional.of(expectedWallet));
        Wallet actualWallet = walletService.getWallet(id);
        assertThat(actualWallet, equalTo(expectedWallet));
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
        assertThat(expectedMessage, equalTo(actualMessage));
    }

    @Test
    void updateWallet_success() {
        Wallet sourceWallet = new Wallet(1L, 2L, "Ivan", "USD", "200.00", "17.08");
        Long id = 1L;

        Mockito.when(walletRepository.existsById(1L))
                .thenReturn(true);
        Mockito.when(walletRepository.findById(Mockito.eq(1L)))
                .thenReturn(Optional.of(sourceWallet));

        walletService.updateWallet(sourceWallet, id);

        Mockito.verify(walletRepository).save(Mockito.eq(sourceWallet));
    }

    @Test
    void updateWallet_failure() {
        Wallet sourceWallet = new Wallet(1L, 2L, "Ivan", "USD", "200.00", "17.08");
        Long id = 1L;

        Mockito.when(walletRepository.existsById(Mockito.eq(id))).thenReturn(false);
        String expectedMessage = "Wallet was not found by id";

        String actualMessage = Assertions.assertThrows(RuntimeException.class, () ->
                walletService.updateWallet(sourceWallet, id)).getMessage();
        assertThat(actualMessage, equalTo(expectedMessage));
    }

}
