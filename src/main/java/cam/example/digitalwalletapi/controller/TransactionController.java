package cam.example.digitalwalletapi.controller;

import cam.example.digitalwalletapi.dto.TransferRequest;
import cam.example.digitalwalletapi.entity.Transaction;
import cam.example.digitalwalletapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        Transaction transaction = transactionService.transfer(
                request.getSenderWalletId(),
                request.getReceiverWalletId(),
                request.getAmount()
        );
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<?> getTransactions(@PathVariable Long walletId) {
        List<Transaction> transactions = transactionService.getTransactionsByWalletId(walletId);
        return ResponseEntity.ok(transactions);
    }
}

