package cam.example.digitalwalletapi.dto;

import java.math.BigDecimal;

public class CreateWalletRequest {
    private Long userId;
    private BigDecimal initialBalance;

    // Constructors
    public CreateWalletRequest() {}

    public CreateWalletRequest(Long userId, BigDecimal initialBalance) {
        this.userId = userId;
        this.initialBalance = initialBalance;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
