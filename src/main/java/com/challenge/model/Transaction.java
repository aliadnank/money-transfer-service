package com.challenge.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class Transaction {

    private UUID id;
    private Status status;
    private Type type;
    private BigDecimal amount;
    private Long customerId;
    private UUID accountId;
    private Instant created;
    private Instant updated;
    private String remarks;

    public Transaction(Status status, Type type, BigDecimal amount, Long customerId, UUID accountId,
                       Instant created, Instant updated, String remarks) {
        this.status = status;
        this.type = type;
        this.amount = amount;
        this.customerId = customerId;
        this.accountId = accountId;
        this.created = created;
        this.updated = updated;
        this.remarks = remarks;
    }

    public static Transaction of(Status status, Type type, BigDecimal amount, Long customerId, UUID accountId,String remarks){
        Transaction transaction = new Transaction(status,type,amount,customerId,accountId,Instant.now(), Instant.now(),remarks);
        transaction.setId(UUID.randomUUID());
        return transaction;
    }

    public enum Status {
        COMPLETED, PROCESSING, FAILED;
    }

    public enum Type {
        WITHDRAWL,DEPOSIT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Transaction that = (Transaction) o;

        if (!id.equals(that.id)) return false;
        if (status != that.status) return false;
        if (type != that.type) return false;
        if (!customerId.equals(that.customerId)) return false;
        return accountId.equals(that.accountId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + customerId.hashCode();
        result = 31 * result + accountId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", amount=" + amount +
                ", customerId=" + customerId +
                ", accountId=" + accountId +
                ", created=" + created +
                ", updated=" + updated +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
