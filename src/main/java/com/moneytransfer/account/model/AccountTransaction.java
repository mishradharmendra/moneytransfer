package com.moneytransfer.account.model;

import java.math.BigDecimal;
import java.time.Instant;

public class AccountTransaction {

    private int transactionId;
    private Instant transactionTime;
    private BigDecimal accountTransaction;
    private TransactionType transactionType;

    public AccountTransaction(int transactionId, Instant transactionTime, BigDecimal accountTransaction, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.transactionTime = transactionTime;
        this.accountTransaction = accountTransaction;
        this.transactionType = transactionType;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Instant getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Instant transactionTime) {
        this.transactionTime = transactionTime;
    }

    public BigDecimal getAccountTransaction() {
        return accountTransaction;
    }

    public void setAccountTransaction(BigDecimal accountTransaction) {
        this.accountTransaction = accountTransaction;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "AccountTransaction{" +
                "transactionId=" + transactionId +
                ", transactionTime=" + transactionTime +
                ", accountTransaction=" + accountTransaction +
                ", transactionType=" + transactionType +
                '}';
    }
}