package mockito.basics.services;

import mockito.basics.models.Wallet;
import mockito.basics.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Transactional
    public void giveMoneyToAll(int amount) {
        var walletList = findAll();
        for (var w : walletList) {
            w.setMoney(w.getMoney() + amount);
        }
    }

    @Transactional
    public void payTaxes(double taxRate) {
        var walletList = findAll();
        for (var w : walletList) {
            w.setMoney((int) Math.floor(w.getMoney() * (1 - taxRate)));
        }
    }
}
