package mockito.basics.controllers;

import mockito.basics.models.Wallet;
import mockito.basics.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;
    private final double taxRate = 0.15;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public List<Wallet> getAllWallets() {
        return walletService.findAll();
    }

    @GetMapping("/giveMoney")
    public List<Wallet> giveMoneyToAll(@RequestParam(name = "amount") int amount) {
        walletService.giveMoneyToAll(amount);
        return walletService.findAll();
    }

    @GetMapping("/payTaxes")
    public List<Wallet> payTaxes() {
        walletService.payTaxes(taxRate);
        return walletService.findAll();
    }
}
