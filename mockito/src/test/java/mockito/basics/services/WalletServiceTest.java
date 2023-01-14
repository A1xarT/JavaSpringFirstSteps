package mockito.basics.services;

import mockito.basics.models.Person;
import mockito.basics.models.Wallet;
import mockito.basics.repositories.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletService walletService;
    private List<Wallet> walletList;

    @BeforeEach
    void configure() {
        Person p1 = new Person("name1", 12), p2 = new Person("name2", 19),
                p3 = new Person("name3", 22), p4 = new Person("name4", 40);
        Wallet w1 = new Wallet(100, p1), w2 = new Wallet(200, p2), w3 = new Wallet(500, p3);
        walletList = List.of(w1, w2, w3);
        Mockito.when(walletRepository.findAll()).thenReturn(walletList);
    }

    @Test
    void findAllShouldEqualsMocked() {
        var listFromService = walletService.findAll();
        for (int i = 0; i < walletList.size(); i++) {
            Assertions.assertEquals(listFromService.get(i), walletList.get(i));
        }
    }

    @Test
    void giveMoneyToAll() {
        final int moneyToAdd = 100;
        var moneyList = walletList.stream().mapToInt(Wallet::getMoney).toArray();
        walletService.giveMoneyToAll(moneyToAdd);
        for (int i = 0; i < moneyList.length; i++) {
            Assertions.assertEquals(moneyList[i], walletList.get(i).getMoney() - moneyToAdd);
        }
    }

    @Test
    void payTaxes() {
        final double taxRate = 0.256;
        var moneyList = walletList.stream().mapToInt(Wallet::getMoney).toArray();
        walletService.payTaxes(taxRate);
        for (int i = 0; i < moneyList.length; i++) {
            Assertions.assertEquals(Math.floor(moneyList[i] * (1 - taxRate)), walletList.get(i).getMoney());
        }
    }
}