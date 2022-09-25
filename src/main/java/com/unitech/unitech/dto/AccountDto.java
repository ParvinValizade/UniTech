package com.unitech.unitech.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDto {
    private String id;
    private BigDecimal balance;
    private LocalDateTime creationDate;
    private AccountStatusDto status;

    public AccountDto() {
    }

    public AccountDto(String id, BigDecimal balance,
                      LocalDateTime creationDate, AccountStatusDto status) {
        this.id = id;
        this.balance = balance;
        this.creationDate = creationDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public AccountStatusDto getStatus() {
        return status;
    }

    public void setStatus(AccountStatusDto status) {
        this.status = status;
    }
}
