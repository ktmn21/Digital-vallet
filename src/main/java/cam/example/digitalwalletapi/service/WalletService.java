package cam.example.digitalwalletapi.service;

import cam.example.digitalwalletapi.entity.User;
import cam.example.digitalwalletapi.entity.Wallet;
import cam.example.digitalwalletapi.repository.UserRepository;
import cam.example.digitalwalletapi.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public Wallet createWallet(Long userId, BigDecimal initialBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Wallet wallet = new Wallet();
        wallet.setBalance(initialBalance);
        wallet.setUser(user);

        return walletRepository.save(wallet);
    }

    public List<Wallet> getWalletsByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }
}

