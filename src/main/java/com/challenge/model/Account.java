package com.challenge.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class Account {

    private UUID id;
    private BigDecimal balance;
    private Long customerId;
    private Instant lastUpdated;
    private Currency currency;

    public Account(BigDecimal balance, Long customerId, Currency currency) {
        this.id = UUID.randomUUID();
        this.balance = balance;
        this.customerId = customerId;
        this.currency = currency;
    }

    public static Account of(Long customerId, BigDecimal availableBalance, Currency currency){
        Account account = new Account(availableBalance,customerId,currency);
        account.setId(UUID.randomUUID());
        return account;
    }

    public enum Currency {
        USD, EUR, PKR, CNY,JPY
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (!id.equals(account.id)) return false;
        if (!customerId.equals(account.customerId)) return false;
        return currency == account.currency;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + customerId.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", customerId=" + customerId +
                ", lastUpdated=" + lastUpdated +
                ", currency=" + currency +
                '}';
    }
}
