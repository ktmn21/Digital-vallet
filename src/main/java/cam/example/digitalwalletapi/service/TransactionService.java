package cam.example.digitalwalletapi.service;

import cam.example.digitalwalletapi.entity.DailyLimit;
import cam.example.digitalwalletapi.entity.Transaction;
import cam.example.digitalwalletapi.entity.Wallet;
import cam.example.digitalwalletapi.repository.DailyLimitRepository;
import cam.example.digitalwalletapi.repository.TransactionRepository;
import cam.example.digitalwalletapi.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DailyLimitRepository dailyLimitRepository;

    public Transaction transfer(Long senderWalletId, Long receiverWalletId, BigDecimal amount) {
        Wallet senderWallet = walletRepository.findById(senderWalletId)
                .orElseThrow(() -> new EntityNotFoundException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findById(receiverWalletId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver wallet not found"));

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Check daily limit
        DailyLimit dailyLimit = dailyLimitRepository.findByUserId(senderWallet.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Daily limit not set"));

        if (dailyLimit.getCurrentTotal().add(amount).compareTo(dailyLimit.getDailyLimit()) > 0) {
            throw new IllegalArgumentException("Daily transaction limit exceeded");
        }

        // Update balances
        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        // Update daily limit
        dailyLimit.setCurrentTotal(dailyLimit.getCurrentTotal().add(amount));

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);
        dailyLimitRepository.save(dailyLimit);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByWalletId(Long walletId) {
        return transactionRepository.findBySenderWalletIdOrReceiverWalletId(walletId, walletId);
    }
}

