package software.sigma.sip.application.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import software.sigma.sip.domain.entity.Wallet;
import software.sigma.sip.domain.repository.WalletRepository;
import software.sigma.sip.infrastructure.dto.WalletDto;
import java.util.Optional;

public class WalletServiceTest {
    private static WalletRepository walletRepository;
    private static WalletService walletService;


    @BeforeAll
    public static void setup(){
        walletRepository = Mockito.mock(WalletRepository.class);
        walletService = new WalletService(walletRepository);
    }
    @Test
    void addWallet_success(){
        Wallet sourceWallet = new Wallet(1L,1L,"Ivan","USD","200.00","17.08");

        Mockito.doReturn(Optional.of(sourceWallet)).when(walletRepository).findById(Mockito.eq(1L));
        walletService.addWallet(sourceWallet);

        Mockito.verify(walletRepository).save(Mockito.eq(sourceWallet));
    }
    @Test
    void getWallet_success(){
        Long id= 1L;
        Wallet expectedWallet = new Wallet(1L,1L,"Ivan","USD","200.00","17.08");
        Mockito.doReturn(Optional.of(expectedWallet))
                .when(walletRepository).findById(Mockito.eq(1L));
        WalletDto walletDtoExpected = WalletDto.toWalletDto(expectedWallet);
        WalletDto actualWallet = walletService.getWallet(id);
        Assertions.assertEquals(actualWallet,walletDtoExpected);


    }
    @Test
    void getWallet_failure(){
        Long id = 1L;

        Mockito.doReturn(Optional.empty())
                .when(walletRepository).findById(Mockito.eq(1L));

        Assertions.assertThrows(RuntimeException.class,()->walletService.getWallet(id));
    }
    @Test
    void deleteWallet_success(){
        Long id = 1L;
        Mockito.doReturn(true).when(walletRepository).existsById(Mockito.eq(id));
        walletService.deleteWallet(id);

        Mockito.verify(walletRepository).deleteById(Mockito.eq(id));
    }
    @Test
    void deleteWallet_failure(){
        Long id = 1L;
        Mockito.doReturn(false).when(walletRepository).existsById(Mockito.eq(id));
        String expectedMessage = "Wallet Does Not Found by ID";
        String actualMessage = Assertions.assertThrows(RuntimeException.class,()->
                walletService.deleteWallet(id)).getMessage();
        Assertions.assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void updateWallet_success(){

        Wallet sourceWallet = new Wallet(1L,2L,"Ivan","USD","200.00","17.08");
        Long id = 1L;

        Mockito.doReturn(true)
                .when(walletRepository).existsById(Mockito.eq(1L));
        Mockito.doReturn(Optional.of(sourceWallet)).when(walletRepository).findById(Mockito.eq(1L));

        walletService.updateWallet(sourceWallet,id);

        Wallet expectedWallet = new Wallet(1L,2L,"Ivan","USD","200.00","17.08");
        Mockito.verify(walletRepository).save(Mockito.eq(expectedWallet));

    }
    @Test
    void updateWallet_failure(){
        Wallet sourceWallet = new Wallet(1L,2L,"Ivan","USD","200.00","17.08");
        Long id = 1L;

        Mockito.doReturn(false).when(walletRepository).existsById(Mockito.eq(id));
        String expectedMessage= "Wallet Does Not Found by ID";

        String actualMessage = Assertions.assertThrows(RuntimeException.class,()->
                walletService.updateWallet(sourceWallet,id)).getMessage();
        Assertions.assertEquals(actualMessage,expectedMessage);
    }

}
