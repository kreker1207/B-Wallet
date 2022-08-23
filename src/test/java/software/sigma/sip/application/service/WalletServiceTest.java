package software.sigma.sip.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.domain.repository.WalletRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
    @Mock
    private static WalletRepository walletRepository;
    @InjectMocks
    private static WalletService walletService;
    @Captor
    ArgumentCaptor<Wallet> walletArgumentCaptor;



    @Test
    void addWallet_success() {
        Wallet sourceWallet = new Wallet(1L, 1L, "Ivan", "USD", "200.00", "17.08");

        walletService.addWallet(sourceWallet);

        Mockito.verify(walletRepository).save(sourceWallet);
    }

    @Test
    void getWallet_success() {
        Wallet source = new Wallet(1L, 1L, "Ivan", "USD", "200.00", "17.08");

        Mockito.when(walletRepository.findById(1L))
                .thenReturn(Optional.of(source));
        Wallet actualWallet = walletService.getWallet(1L);
        assertThat(actualWallet).isEqualTo(source);
    }

    @Test
    void getWallet_failure() {
        Mockito.when(walletRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> walletService.getWallet(1L));
    }

    @Test
    void deleteWallet_success() {
        Long id = 1L;
        Mockito.when(walletRepository.existsById(id)).thenReturn(true);
        walletService.deleteWallet(1L);

        Mockito.verify(walletRepository).deleteById(1L);
    }

    @Test
    void deleteWallet_failure() {
        Mockito.when(walletRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "Wallet was not found by id";
        String actualMessage = Assertions.assertThrows(RuntimeException.class, () ->
                walletService.deleteWallet(1L)).getMessage();
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    void updateWallet_success() {
        Wallet sourceWallet = new Wallet(1L, 2L, "Ivan", "USD", "200.00", "17.08");
        Wallet expected = new Wallet(1L, 2L, "Ivan", "USD", "200.00", "17.08");

        Mockito.when(walletRepository.existsById(1L))
                .thenReturn(true);
        Mockito.when(walletRepository.findById(1L))
                .thenReturn(Optional.of(sourceWallet));

        walletService.updateWallet(sourceWallet, 1L);
        Mockito.verify(walletRepository).save(walletArgumentCaptor.capture());
        assertThat(walletArgumentCaptor.getValue()).isEqualTo(expected);

    }

    @Test
    void updateWallet_failure() {
        Wallet sourceWallet = new Wallet(1L, 2L, "Ivan", "USD", "200.00", "17.08");
        Long id = 1L;

        Mockito.when(walletRepository.existsById(Mockito.eq(id))).thenReturn(false);
        String expectedMessage = "Wallet was not found by id";

        String actualMessage = Assertions.assertThrows(RuntimeException.class, () ->
                walletService.updateWallet(sourceWallet, 1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}
