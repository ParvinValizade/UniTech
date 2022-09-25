package com.unitech.unitech.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private BigDecimal balance;
    private LocalDateTime creationDate;
    private AccountStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User userId;

    public Account() {
    }

    public Account(String id, BigDecimal balance, LocalDateTime creationDate,
                   AccountStatus status, User userId) {
        this.id = id;
        this.balance = balance;
        this.creationDate = creationDate;
        this.status = status;
        this.userId = userId;
    }

    public Account(BigDecimal balance, LocalDateTime creationDate,
                   AccountStatus status, User userId) {
        this.balance = balance;
        this.creationDate = creationDate;
        this.status = status;
        this.userId = userId;
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

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return  getBalance().equals(account.getBalance())
                && getStatus() == account.getStatus()
                && getUserId().equals(account.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getBalance(),getStatus(), getUserId());
    }
}
